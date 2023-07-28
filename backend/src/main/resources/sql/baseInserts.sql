BEGIN;

-- DEFAULT VALUES
INSERT INTO daw_State(name) VALUES ('archived'), ('closed'), ('opened');
--INSERT INTO daw_User(username, password) VALUES ('chriptus13', 'andre12345'), ('cbartolomeu', '12345'), ('marcomartins1998', '12345');
INSERT INTO daw_User(username, password) VALUES ('1', 'andre12345'), ('2', '12345'), ('3', '12345'), ('90342.ASDFJWFA', 'password'), ('01921.FLANRJQW', 'password');
-- FOR TEST PORPUSES
--SELECT create_project('testProject', 'test', 'opened', 1); bugados
--INSERT INTO daw_Project_User(project, "user") VALUES (1, 2), (1, 3);
--INSERT INTO daw_Transition(project, startState, endState) VALUES (1, 3, 2);

COMMIT;