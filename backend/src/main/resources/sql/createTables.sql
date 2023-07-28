BEGIN;

CREATE TABLE daw_User (
	id SERIAL,
	username VARCHAR(31) UNIQUE NOT NULL,
	password VARCHAR(511) NOT NULL,
	salt VARCHAR(255) NULL,
	
	CONSTRAINT PK_User PRIMARY KEY (id)
);

CREATE TABLE daw_State (
	id SERIAL,
	name VARCHAR(63) UNIQUE NOT NULL,
	
	CONSTRAINT PK_State PRIMARY KEY (id)
);

CREATE TABLE daw_Project (
	id SERIAL,
	name VARCHAR(63) UNIQUE NOT NULL,
	description VARCHAR(255) NULL,
	initialState INT NULL,
	
	CONSTRAINT PK_Project PRIMARY KEY (id),
	CONSTRAINT FK_Project_State FOREIGN KEY (initialState) REFERENCES daw_State(id)
);

CREATE TABLE daw_Project_State (
	id SERIAL,
	project INT NOT NULL,
	"state" INT NOT NULL,
	
	CONSTRAINT PK_Project_State PRIMARY KEY (id),
	CONSTRAINT UQ_Project_State UNIQUE (project, "state"),
	CONSTRAINT FK_Project_State_Project FOREIGN KEY (project) REFERENCES daw_Project(id) ON DELETE CASCADE,
	CONSTRAINT FK_Project_State_State FOREIGN KEY ("state") REFERENCES daw_State(id) ON DELETE CASCADE
);

CREATE TABLE daw_Project_User (
	id SERIAL,
	project INT NOT NULL,
	"user" INT NOT NULL,

	CONSTRAINT PK_Project_User PRIMARY KEY (id),
	CONSTRAINT UQ_Project_User UNIQUE (project, "user"),
	CONSTRAINT FK_Project_User_Project FOREIGN KEY (project) REFERENCES daw_Project(id) ON DELETE CASCADE,
	CONSTRAINT FK_Project_User_User FOREIGN KEY ("user") REFERENCES daw_User(id) ON DELETE CASCADE
);

CREATE TABLE daw_Label (
	id SERIAL,
	project INT NOT NULL,
	name VARCHAR(63) NOT NULL,
	
	CONSTRAINT PK_Label PRIMARY KEY (id),
	CONSTRAINT UQ_Label UNIQUE (project, name),
	CONSTRAINT FK_Label_Project FOREIGN KEY (project) REFERENCES daw_Project(id) ON DELETE CASCADE
);

CREATE TABLE daw_Issue (
	id SERIAL,
	project INT NOT NULL,
	creator INT NOT NULL,
	name VARCHAR(63) NOT NULL,
	description VARCHAR(1023) NOT NULL,
	created TIMESTAMP NOT NULL DEFAULT LOCALTIMESTAMP(0),
	closed TIMESTAMP NULL,
	"state" INT NULL,
	
	CONSTRAINT PK_Issue PRIMARY KEY (id),
	CONSTRAINT FK_Issue_Project FOREIGN KEY (project) REFERENCES daw_Project(id) ON DELETE CASCADE,
	CONSTRAINT FK_Issue_User FOREIGN KEY (creator) REFERENCES daw_User(id),
	CONSTRAINT FK_Issue_State FOREIGN KEY ("state") REFERENCES daw_State(id)
);

CREATE TABLE daw_Issue_Label (
	id SERIAL,
	issue INT NOT NULL,
	label INT NOT NULL,
	
	CONSTRAINT PK_Issue_Label PRIMARY KEY (id),
	CONSTRAINT UQ_Issue_Label UNIQUE (issue, label),
	CONSTRAINT FK_Issue_Label_Issue FOREIGN KEY (issue) REFERENCES daw_Issue(id) ON DELETE CASCADE,
	CONSTRAINT FK_Issue_Label_Label FOREIGN KEY (label) REFERENCES daw_Label(id) ON DELETE CASCADE
);

CREATE TABLE daw_Transition (
	id SERIAL,
	project INT NOT NULL,
	startState INT NOT NULL,
	endState INT NOT NULL,
	
	CONSTRAINT PK_Transition PRIMARY KEY (id),
	CONSTRAINT CK_Transition CHECK (startState <> endState),
	CONSTRAINT UQ_Transition UNIQUE (project, startState, endState),
	CONSTRAINT FK_Transition_Project FOREIGN KEY (project) REFERENCES daw_Project(id) ON DELETE CASCADE,
	CONSTRAINT FK_Transition_State_start FOREIGN KEY (startState) REFERENCES daw_State(id) ON DELETE CASCADE,
	CONSTRAINT FK_Transition_State_end FOREIGN KEY (endState) REFERENCES daw_State(id) ON DELETE CASCADE
);

CREATE TABLE daw_Comment (
	id SERIAL,
	issue INT NOT NULL,
	creator INT NOT NULL,
	text VARCHAR(255) NOT NULL,
	created TIMESTAMP NOT NULL DEFAULT LOCALTIMESTAMP(0),
	
	CONSTRAINT PK_Comment PRIMARY KEY (id),
	CONSTRAINT FK_Comment_Issue FOREIGN KEY (issue) REFERENCES daw_Issue(id) ON DELETE CASCADE,
	CONSTRAINT FK_Comment_User FOREIGN KEY (creator) REFERENCES daw_User(id)
);

COMMIT;