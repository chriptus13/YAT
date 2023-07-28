BEGIN;

/* +++ UPDATE restrictions +++ */

CREATE TRIGGER project_state_restrict_update
	BEFORE UPDATE
	ON daw_Project_State
	EXECUTE PROCEDURE restrict_update();

CREATE TRIGGER project_user_restrict_update
	BEFORE UPDATE
	ON daw_Project_User
	EXECUTE PROCEDURE restrict_update();

CREATE TRIGGER transition_restrict_update
	BEFORE UPDATE
	ON daw_Transition
	EXECUTE PROCEDURE restrict_update();

CREATE TRIGGER issue_label_restrict_update
	BEFORE UPDATE
	ON daw_Issue_Label
	EXECUTE PROCEDURE restrict_update();

CREATE TRIGGER issue_restrict_update
	BEFORE UPDATE
	ON daw_Issue
	FOR EACH ROW
	WHEN (NEW.project <> OLD.project 
		OR NEW.creator <> OLD.creator 
		OR NEW.created <> OLD.created 
		OR (OLD.closed IS NOT NULL AND NEW.closed <> OLD.closed)
	)
	EXECUTE PROCEDURE restrict_update();

CREATE TRIGGER comment_restrict_update
	BEFORE UPDATE
	ON daw_Comment
	FOR EACH ROW
	WHEN (NEW.issue <> OLD.issue 
		OR NEW.creator <> OLD.creator 
		OR NEW.created <> OLD.created
	)
	EXECUTE PROCEDURE restrict_update();

/* +++ Domain Integrity Rules +++ */

CREATE TRIGGER project_insert_default_transition
	AFTER INSERT
	ON daw_Project
	FOR EACH ROW
	EXECUTE PROCEDURE project_insert_default_transition();
	
CREATE TRIGGER project_validade_state
	BEFORE UPDATE
	ON daw_Project
	FOR EACH ROW
	EXECUTE PROCEDURE project_validate_state();

CREATE TRIGGER issue_insert_initial_state
	BEFORE INSERT
	ON daw_Issue
	FOR EACH ROW
	EXECUTE PROCEDURE issue_insert_initial_state();
	
CREATE TRIGGER issue_update_validate_state
	BEFORE UPDATE
	ON daw_Issue
	FOR EACH ROW
	WHEN (NEW.state <> OLD.state)
	EXECUTE PROCEDURE issue_update_validate_state();

CREATE TRIGGER issue_label_validate_same_project
	BEFORE INSERT
	ON daw_Issue_Label
	FOR EACH ROW
	EXECUTE PROCEDURE issue_label_validate_same_project();
	
CREATE TRIGGER transition_validate_same_project
	BEFORE INSERT
	ON daw_Transition
	FOR EACH ROW
	EXECUTE PROCEDURE transition_validate_same_project();
	
CREATE TRIGGER comment_check_archived_issue
	BEFORE INSERT
	ON daw_Comment
	FOR EACH ROW
	EXECUTE PROCEDURE comment_check_archived_issue();

COMMIT;