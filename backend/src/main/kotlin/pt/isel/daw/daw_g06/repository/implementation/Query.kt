package pt.isel.daw.daw_g06.repository.implementation

object Query {

    /* ** INSERTS ** */
    const val INSERT_PROJECT = "INSERT INTO daw_project(name, description) VALUES (?, ?) RETURNING id"
    const val INSERT_PROJECT_WITH_INITIALSTATE = "SELECT create_project_with_initialstate(?, ?, ?)"
    const val INSERT_ISSUE = "INSERT INTO daw_issue(project, creator, name, description) VALUES (?, ?, ?, ?) RETURNING id"
    const val INSERT_PROJECT_USER = "INSERT INTO daw_project_user(project, \"user\") VALUES (?, ?)"
    const val INSERT_PROJECT_LABEL = "INSERT INTO daw_label(project, name) VALUES (?, ?)"
    const val INSERT_ISSUE_LABEL = "SELECT add_issue_label(?, ?, ?)"
    const val INSERT_COMMENT = "INSERT INTO daw_comment(issue, creator, text) VALUES (?, ?, ?) RETURNING id"

    const val ADD_PROJECT_STATE = "SELECT add_project_state(?, ?)"
    const val ADD_PROJECT_TRANSITION = "SELECT add_project_transition(?, ?, ?)"

    /* ** SELECTS ** */
    const val SELECT_USERS = "SELECT id, username, password FROM daw_user"
    const val SELECT_USER = "SELECT id, username, password FROM daw_user WHERE id = ?"
    const val SELECT_USER_BY_USERNAME = "SELECT id, username, password FROM daw_user WHERE username = ?"
    const val SELECT_USER_AUTH = "SELECT id FROM daw_user WHERE username = ? AND password = ?"
    const val SELECT_PROJECTS_FROM_USER = """
        SELECT P.id, P.name, P.description, S.name initialState FROM daw_project P
        INNER JOIN daw_project_user PU ON (P.id = PU.project)
        LEFT JOIN daw_state S ON (P.initialState = S.id)
        WHERE PU.user = ?
    """
    const val SELECT_PROJECTS = """
        SELECT P.id, P.name, P.description, S.name initialState FROM daw_project P
        LEFT JOIN daw_state S ON (P.initialState = S.id)
    """
    const val SELECT_PROJECT = """
        SELECT P.id, P.name, P.description, S.name initialState FROM daw_project P
        LEFT JOIN daw_state S ON (P.initialState = S.id)
        WHERE P.id = ?
    """
    const val SELECT_PROJECT_USER = "SELECT id FROM daw_project_user WHERE project = ? AND \"user\" = ?"
    const val SELECT_PROJECT_LABELS = "SELECT id, project, name FROM daw_label WHERE project = ?"
    const val SELECT_ISSUES = """
        SELECT I.id, I.project, U.username creator, I.name, I.description, I.created, I.closed, S.name state FROM daw_issue I
        INNER JOIN daw_user U ON (I.creator = U.id)
        INNER JOIN daw_state S ON (I.state = S.id)
        WHERE I.project = ?
        """
    const val SELECT_ISSUE = """
        SELECT I.id, I.project, U.username creator, I.name, I.description, I.created, I.closed, S.name state FROM daw_issue I
        INNER JOIN daw_user U ON (I.creator = U.id)
        INNER JOIN daw_state S ON (I.state = S.id)
        WHERE I.project = ? AND I.id = ?
        """
    const val SELECT_ISSUE_LABELS = """
        SELECT L.id, L.project, L.name FROM daw_issue_label IL
        INNER JOIN daw_label L ON (IL.label = L.id)
        WHERE L.project = ? AND IL.issue = ?
        """
    const val SELECT_PROJECT_MEMBERS = """
        SELECT PU.project, U.username FROM daw_project_user PU
        INNER JOIN daw_user U ON (PU.user = U.id)
        WHERE PU.project = ?
    """
    const val SELECT_PROJECT_STATES = """
        SELECT PS.id, PS.project, S.name FROM daw_project_state PS
        INNER JOIN daw_state S ON (PS.state = S.id)
        WHERE PS.project = ?
    """
    const val SELECT_PROJECT_TRANSITIONS = """
        SELECT T.id, T.project, S1.name startState, S2.name endState FROM daw_transition T
        INNER JOIN daw_state S1 ON (T.startState = S1.id)
        INNER JOIN daw_state S2 ON (T.endState = S2.id)
        WHERE T.project = ?
    """
    const val SELECT_PROJECT_TRANSITION = """
        SELECT T.id, T.project, S1.name startState, S2.name endState FROM daw_transition T
        INNER JOIN daw_state S1 ON (T.startState = S1.id)
        INNER JOIN daw_state S2 ON (T.endState = S2.id)
        WHERE T.project = ? AND T.id = ?
    """
    const val SELECT_COMMENTS = """
        SELECT C.id, C.issue, U.username creator, C.text, C.created FROM daw_comment C
        INNER JOIN daw_user U ON (C.creator = U.id)
        WHERE C.issue = ?
    """
    const val SELECT_COMMENT = """
        SELECT C.id, C.issue, U.username creator, C.text, C.created FROM daw_comment C
        INNER JOIN daw_user U ON (C.creator = U.id)
        WHERE C.id = ?
    """


    /* ** UPDATES ** */

    // Project
    const val UPDATE_PROJECT = """
        UPDATE daw_project SET name = ?, description = ?, initialState = get_state_id_or_throw(?)
        WHERE id = ?
    """
    const val UPDATE_COMMENT = "UPDATE daw_comment SET text = ? WHERE id = ?"


    // Issue
    const val UPDATE_ISSUE = "UPDATE daw_issue SET name = ?, description = ?, state = get_state_id_or_throw(?) WHERE project = ? AND id = ?"

    /* ** DELETES ** */
    const val DELETE_PROJECT = "DELETE FROM daw_project WHERE id = ?"
    const val DELETE_PROJECT_USER = "DELETE FROM daw_project_user WHERE project = ? AND \"user\" = ?"
    const val DELETE_PROJECT_LABEL = "DELETE FROM daw_label WHERE project = ? AND name = ?"
    const val DELETE_ISSUE = "DELETE FROM daw_issue WHERE project = ? AND id = ?"

    const val DELETE_ISSUE_LABEL = "DELETE FROM daw_issue_label WHERE issue = ? AND label = (SELECT id FROM daw_label WHERE project = ? AND name = ?)"

    const val REMOVE_PROJECT_STATE = "SELECT remove_project_state(?, ?)"
    const val REMOVE_PROJECT_TRANSITION = "SELECT remove_project_transition(?, ?)"
    const val DELETE_COMMENT = "DELETE FROM daw_comment WHERE id = ?"
}