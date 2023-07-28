# Domain

## **Data Types**

### 🠞 Number

Data type: Number.

### 🠞 Text

Data type: Text.

### 🠞 Date

A date value in [ISO 8601 date format](https://en.wikipedia.org/wiki/ISO_8601).

## **Entities**

### 🠞 Project

Represents a Project.

| Property     | Expected Type | Description                                                      |
| ------------ | ------------- | ---------------------------------------------------------------- |
| id *         | Number        | The unique identifier for the Project.                           |
| name *       | Text          | The name of the Project.                                         |
| description  | Text          | The description of the Project.                                  |
| initialState | Text          | The State in which newly created Issues for the Project will be. |

* Users - `List<User>`
* Issues - `List<Issue>`
* Labels - `List<Label>`
* States - `List<State>`
* Transitions - `List<Transition>`

### 🠞 Label

Represents a Project Label.

| Property | Expected Type | Description                                         |
| -------- | ------------- | --------------------------------------------------- |
| name *   | Text          | The name of the Label which is a unique identifier. |

* Project - `Project`

### 🠞 State

Represents a Project State.

| Property | Expected Type | Description                                         |
| -------- | ------------- | --------------------------------------------------- |
| name *   | Text          | The name of the State which is a unique identifier. |

* Project - `Project`

### 🠞 Transition

Represents a valid State transition for the Issue's State in a Project.

| Property     | Expected Type | Description                                                 |
| ------------ | ------------- | ----------------------------------------------------------- |
| startState * | Text          | The State in which the Issue must be before the transition. |
| endState *   | Text          | The State in which the Issue will be after the transition.  |

* Project - `Project`

### 🠞 Issue

Represents a Project Issue.

| Property      | Expected Type | Description                                |
| ------------- | ------------- | ------------------------------------------ |
| id *          | Number        | The unique identifier for the Issue.       |
| creator *     | Text          | The User's username who created the Issue. |
| name *        | Text          | The name of the Issue.                     |
| description * | Text          | The description of the Issue.              |
| created *     | Date          | The date in which the Issue was created.   |
| closed        | Date          | The date in which the Issue was closed.    |
| state         | Text          | The current State of the Issue.            |

* Labels - `List<Label>`
* Comments - `List<Comment>`

### 🠞 Comment

Represents an Issue Comment.

| Property  | Expected Type | Description                                  |
| --------- | ------------- | -------------------------------------------- |
| id *      | Number        | The unique identifier for the Comment.       |
| creator * | Text          | The User's username who created the Comment. |
| text *    | Text          | The text of the Comment.                     |
| created * | Date          | The date in which the Comment was created.   |

### 🠞 User

Represents a Project User.

| Property   | Expected Type | Description                                            |
| ---------- | ------------- | ------------------------------------------------------ |
| username * | Text          | The username of the User which is a unique identifier. |