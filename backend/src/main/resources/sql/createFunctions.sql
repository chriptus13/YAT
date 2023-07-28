BEGIN;
-- https://stackoverflow.com/questions/22594395/custom-error-code-class-range-in-postgresql -- Reference for ERRCODES
-- https://www.postgresql.org/docs/current/plpgsql-errors-and-messages.html

CREATE FUNCTION restrict_update() RETURNS TRIGGER
AS $$ 
BEGIN
	RAISE EXCEPTION 'Forbiden UPDATE on table %s', TG_TABLE_NAME USING ERRCODE = 'DAWA0';
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION issue_insert_initial_state() RETURNS TRIGGER
AS $$
DECLARE
    stateId INT := (SELECT initialState FROM daw_Project where id = NEW.project);
BEGIN
    IF stateId IS NULL THEN
        RAISE EXCEPTION 'Project initial state not set!' USING ERRCODE = 'DAWA9';
    END IF;
	NEW.state := stateId;
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION issue_label_validate_same_project() RETURNS TRIGGER
AS $$
DECLARE 
    pissue INT := (SELECT project FROM daw_Issue WHERE id = NEW.issue);
    plabel INT := (SELECT project FROM daw_Label WHERE id = NEW.label);
BEGIN
    IF pissue <> plabel THEN
        RAISE EXCEPTION 'Issue and Label from different projects.' USING ERRCODE = 'DAWA1';
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION transition_validate_same_project() RETURNS TRIGGER
AS $$
BEGIN
    IF NEW.startState = NEW.endState THEN
        RAISE EXCEPTION 'Invalid transition!' USING ERRCODE = 'DAWA2';
    END IF;
    IF NOT EXISTS (SELECT id FROM daw_Project_State WHERE project = NEW.project AND state = NEW.startState) THEN
        RAISE EXCEPTION 'Start state of given transition not in the set of available states of the project!' USING ERRCODE = 'DAWA3';
    END IF;
    IF NOT EXISTS (SELECT id FROM daw_Project_State WHERE project = NEW.project AND state = NEW.endState) THEN
        RAISE EXCEPTION 'End state of given transition not in the set of available states of the project!' USING ERRCODE = 'DAWA4';
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION comment_check_archived_issue() RETURNS TRIGGER
AS $$
BEGIN
	IF EXISTS (SELECT I.id FROM daw_Issue I WHERE I.id = NEW.issue AND state = (SELECT id FROM daw_State S WHERE S.name = 'archived')) THEN
		RAISE EXCEPTION 'Comments cannot be added to archived issues!' USING ERRCODE = 'DAWA5';
	END IF;
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION get_state_id("state" VARCHAR(63)) RETURNS INT
AS 'SELECT id FROM daw_State WHERE name = $1' LANGUAGE SQL;

CREATE FUNCTION get_state_id_or_throw("state" VARCHAR(63)) RETURNS INT
AS $$
DECLARE
	stateId INT = get_state_id("state");
BEGIN
	if stateId IS NULL THEN
		RAISE EXCEPTION 'Invalid state!' USING ERRCODE = 'DAWAA';
	END IF;
	RETURN stateId;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION issue_update_validate_state() RETURNS TRIGGER
AS $$
DECLARE
    closedStateId INT := (SELECT get_state_id('closed'));
BEGIN
	IF NOT EXISTS (SELECT id FROM daw_Transition WHERE project = OLD.project AND startState = OLD.state AND endState = NEW.state) THEN
		RAISE EXCEPTION 'Invalid transition!' USING ERRCODE = 'DAWA2';
	END IF;
    IF NEW.state = closedStateId THEN
        NEW.closed = LOCALTIMESTAMP(0);
    END IF;
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION project_insert_default_transition() RETURNS TRIGGER
AS $$
DECLARE
    closedStateId INT := (SELECT get_state_id('closed'));
    archivedStateId INT := (SELECT get_state_id('archived'));
BEGIN
    INSERT INTO daw_Project_State(project, state) VALUES (NEW.id, closedStateId), (NEW.id, archivedStateId);
    INSERT INTO daw_Transition(project, startState, endState) VALUES (NEW.id, closedStateId, archivedStateId);
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION project_validate_state() RETURNS TRIGGER
AS $$
BEGIN
    IF NEW.initialState <> OLD.initialState AND NOT EXISTS (SELECT id FROM daw_Project_State WHERE "state" = NEW.initialState) THEN
		RAISE EXCEPTION 'Invalid state!' USING ERRCODE = 'DAWAA';
	END IF;
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION create_project_with_initialstate(name VARCHAR(63), description VARCHAR(255), initialStateName VARCHAR(63)) RETURNS INT
AS $$
DECLARE
	pid INT;
BEGIN
	INSERT INTO daw_project(name, description) VALUES (name, description) RETURNING "id" INTO pid;
	UPDATE daw_project SET initialState = add_project_state(pid, initialStateName) WHERE "id" = pid;
	RETURN pid;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION add_project_state(pid INT, stateToAdd VARCHAR(63)) RETURNS INT
AS $$
DECLARE
    stateId INT := (SELECT get_state_id(stateToAdd));
BEGIN
	IF EXISTS (SELECT id FROM daw_Project_State PS WHERE PS.project = pid AND PS.state = get_state_id(stateToAdd)) THEN
		RAISE EXCEPTION 'Invalid state!' USING ERRCODE = 'DAWAA';
	END IF;
    IF stateId IS NULL THEN
        INSERT INTO daw_State(name) VALUES (stateToAdd) RETURNING "id" INTO stateId;
    END IF;
    INSERT INTO daw_Project_State(project, "state") VALUES (pid, stateId);
	RETURN stateId;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION remove_project_state(pid INT, stateToRemove VARCHAR(63)) RETURNS VOID
AS $$
DECLARE
    stateId INT := (SELECT id FROM daw_Project_State PS WHERE PS.project = pid AND PS.state = get_state_id_or_throw(stateToRemove));
    closedStateId INT := (SELECT id FROM daw_Project_State PS where PS.project = pid AND PS.state = get_state_id('closed'));
    archivedStateId INT := (SELECT id FROM daw_Project_State PS where PS.project = pid AND PS.state = get_state_id('archived'));
BEGIN
    IF stateId = closedStateId OR stateId = archivedStateId THEN
        RAISE EXCEPTION 'Cannot remove default states!' USING ERRCODE = 'DAWA6';
    END IF;
	IF EXISTS (SELECT name FROM daw_State WHERE id = (SELECT initialState FROM daw_Project P where P.id = pid) and name=stateToRemove) THEN
		RAISE EXCEPTION 'Cannot remove initial state!' USING ERRCODE = 'DAWA6';
	END IF;
    IF stateId IS NOT NULL THEN
        DELETE FROM daw_Project_State WHERE id = stateId;
    END IF;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION add_project_transition(pid INT, startState VARCHAR(63), endState VARCHAR(63)) RETURNS INT
AS $$
DECLARE
    startStateId INT := (SELECT get_state_id_or_throw(startState));
    endStateId INT := (SELECT get_state_id_or_throw(endState));
	tid INT;
BEGIN
    INSERT INTO daw_Transition(project, startState, endState) VALUES (pid, startStateId, endStateId) RETURNING id INTO tid;
	RETURN tid;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION remove_project_transition(pid INT, removalTransitionId INT) RETURNS VOID
AS $$
DECLARE
    noneRemovalTransitionId INT := (SELECT id FROM daw_Transition 
        WHERE project = pid AND startState = get_state_id('closed') AND endState = get_state_id('archived'));
BEGIN
    IF noneRemovalTransitionId = removalTransitionId THEN
        RAISE EXCEPTION 'Cannot remove default transition!' USING ERRCODE = 'DAWA7';
    END IF;
    DELETE FROM daw_Transition WHERE id = removalTransitionId;
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION add_issue_label(iid INT, pid INT, label VARCHAR(63)) RETURNS VOID
AS $$
DECLARE
    projectLabelId INT := (SELECT id FROM daw_label WHERE project = pid AND name = label);
BEGIN
    IF projectLabelId IS NULL THEN
        RAISE EXCEPTION 'Invalid project label!' USING ERRCODE = 'DAWA8';
    END IF;
    INSERT INTO daw_issue_label(issue, label) VALUES (iid, projectLabelId);
END
$$ LANGUAGE plpgsql;


COMMIT;