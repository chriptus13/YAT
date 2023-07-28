# API Endpoints

* [GETs](#gets)
* [POSTs](#posts)
* [PUTs](#puts)
* [PATCHes](#patches)
* [DELETEs](#deletes)

___

## **GETs**

* [Get all projects](#get-all-projects)
* [Get project](#get-project)
* [Get project issues](#get-project-issues)
* [Get project issue](#get-project-issue)
* [Get project issue labels](#get-project-issue-labels)
* [Get project issue comments](#get-project-issue-comments)
* [Get project issue comment](#get-project-issue-comment)
* [Get project labels](#get-project-labels)
* [Get project states](#get-project-states)
* [Get project transitions](#get-project-transitions)
* [Get project transition](#get-project-transition)
* [Get project members](#get-project-members)

### Get all projects

#### 🠞 Request

* Method: GET
* Path: `/projects`

#### 🠞 Response

| Status |         Content-Type         |                                     Body                                      |
| :----: | :--------------------------: | :---------------------------------------------------------------------------: |
|  200   |      _application/json_      |     [Example](/docs/representations/output/ApplicationJson/projects.json)     |
|        | _application/vnd.siren+json_ |          [Example](/docs/representations/output/Siren/projects.json)          |
|        |    _application/hal+json_    |           [Example](/docs/representations/output/HAL/projects.json)           |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json) |

[GETs](#gets)

### Get project

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/project.json)      |
|        | _application/vnd.siren+json_ |           [Example](/docs/representations/output/Siren/project.json)           |
|        |    _application/hal+json_    |            [Example](/docs/representations/output/HAL/project.json)            |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project issues

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/issues`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/issues.json)       |
|        | _application/vnd.siren+json_ |       [Example](/docs/representations/output/Siren/project_issues.json)        |
|        |    _application/hal+json_    |            [Example](/docs/representations/output/HAL/issues.json)             |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project issue

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/issues/{iid}`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |       [Example](/docs/representations/output/ApplicationJson/issue.json)       |
|        | _application/vnd.siren+json_ |        [Example](/docs/representations/output/Siren/project_issue.json)        |
|        |    _application/hal+json_    |             [Example](/docs/representations/output/HAL/issue.json)             |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|        |  _application/problem+json_  |    [Example](/docs/representations/output/ProblemJson/issue_not_found.json)    |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project issue labels

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/issues/{iid}/labels`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/labels.json)       |
|        | _application/vnd.siren+json_ |       [Example](/docs/representations/output/Siren/project_labels.json)        |
|        |    _application/hal+json_    |         [Example](/docs/representations/output/HAL/issue_labels.json)          |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|        |  _application/problem+json_  |    [Example](/docs/representations/output/ProblemJson/issue_not_found.json)    |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project issue comments

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/issues/{iid}/comments`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |     [Example](/docs/representations/output/ApplicationJson/comments.json)      |
|        | _application/vnd.siren+json_ |   [Example](/docs/representations/output/Siren/project_issue_comments.json)    |
|        |    _application/hal+json_    |           [Example](/docs/representations/output/HAL/comments.json)            |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|        |  _application/problem+json_  |    [Example](/docs/representations/output/ProblemJson/issue_not_found.json)    |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project issue comment

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/issues/{iid}/comments/{cid}`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/comment.json)      |
|        | _application/vnd.siren+json_ |    [Example](/docs/representations/output/Siren/project_issue_comment.json)    |
|        |    _application/hal+json_    |            [Example](/docs/representations/output/HAL/comment.json)            |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|        |  _application/problem+json_  |    [Example](/docs/representations/output/ProblemJson/issue_not_found.json)    |
|        |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/comment_not_found.json)   |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project labels

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/labels`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/labels.json)       |
|        | _application/vnd.siren+json_ |       [Example](/docs/representations/output/Siren/project_labels.json)        |
|        |    _application/hal+json_    |        [Example](/docs/representations/output/HAL/project_labels.json)         |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|        |  _application/problem+json_  |    [Example](/docs/representations/output/ProblemJson/issue_not_found.json)    |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project states

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/states`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/states.json)       |
|        | _application/vnd.siren+json_ |       [Example](/docs/representations/output/Siren/project_states.json)        |
|        |    _application/hal+json_    |            [Example](/docs/representations/output/HAL/states.json)             |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project transitions

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/transitions`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |    [Example](/docs/representations/output/ApplicationJson/transitions.json)    |
|        | _application/vnd.siren+json_ |     [Example](/docs/representations/output/Siren/project_transitions.json)     |
|        |    _application/hal+json_    |          [Example](/docs/representations/output/HAL/transitions.json)          |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project transition

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/transitions/{tid}`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |    [Example](/docs/representations/output/ApplicationJson/transition.json)     |
|        | _application/vnd.siren+json_ |     [Example](/docs/representations/output/Siren/project_transition.json)      |
|        |    _application/hal+json_    |          [Example](/docs/representations/output/HAL/transition.json)           |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|        |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/transition_not_found.json)  |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

### Get project members

#### 🠞 Request

* Method: GET
* Path: `/projects/{pid}/members`

#### 🠞 Response

| Status |         Content-Type         |                                      Body                                      |
| :----: | :--------------------------: | :----------------------------------------------------------------------------: |
|  200   |      _application/json_      |      [Example](/docs/representations/output/ApplicationJson/members.json)      |
|        | _application/vnd.siren+json_ |       [Example](/docs/representations/output/Siren/project_members.json)       |
|        |    _application/hal+json_    |            [Example](/docs/representations/output/HAL/members.json)            |
|  401   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   |  _application/problem+json_  |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|  503   |  _application/problem+json_  | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[GETs](#gets)

___

## POSTs

* [Create project](#create-project)
* [Create project transition](#create-project-transition)
* [Create project issue](#create-project-issue)
* [Create project issue comment](#create-project-issue-comment)

### Create project

#### 🠞 Request

* Method: POST
* Content-Type: _application/json_
* Path: `/projects`
* Body: [Example](/docs/representations/input/ApplicationJson/project.json)

#### 🠞 Response

| Status |        Content-Type        |                                      Body                                      |
| :----: | :------------------------: | :----------------------------------------------------------------------------: |
|  201   |             -              |                                       -                                        |
|  401   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  409   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/project_conflict.json)    |
|  503   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

[POSTs](#posts)

### Create project transition

#### 🠞 Request

* Method: POST
* Content-Type: _application/json_
* Path: `/projects/{pid}/transition`
* Body: [Example](/docs/representations/input/ApplicationJson/transition.json)

#### 🠞 Response

| Status |        Content-Type        |                                        Body                                         |
| :----: | :------------------------: | :---------------------------------------------------------------------------------: |
|  201   |             -              |                                          -                                          |
|  400   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/add_transition_bad_request.json) |
|  401   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)    |
|  403   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json)  |
|  404   | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/project_not_found.json)      |
|  503   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/database_access_error.json)    |

[POSTs](#posts)### Create project issue

#### 🠞 Request

* Method: POST
* Content-Type: _application/json_
* Path: `/projects/{pid}/issues`
* Body: [Example](/docs/representations/input/ApplicationJson/issue.json)

#### 🠞 Response

| Status |        Content-Type        |                                      Body                                      |
| :----: | :------------------------: | :----------------------------------------------------------------------------: |
|  201   |             -              |                                       -                                        |
|  400   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/add_issue_bad_request.json) |
|  401   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)  |
|  404   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/project_not_found.json)   |
|  503   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/database_access_error.json) |

### Create project issue comment

#### 🠞 Request

* Method: POST
* Content-Type: _application/json_
* Path: `/projects/{pid}/issues/{iid}/comments`
* Body: [Example](/docs/representations/input/ApplicationJson/issue_comment.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                       |
| :----: | :------------------------: | :------------------------------------------------------------------------------: |
|  201   |             -              |                                        -                                         |
|  400   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/add_comment_bad_request.json) |
|  401   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)    |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)  |

[POSTs](#posts)

___

## **PUTs**

* [Update project](#update-project)
* [Update project issue](#update-project-issue)
* [Update project issue comment](#update-project-issue-comment)

### Update project

#### 🠞 Request

* Method: PUT
* Content-Type: _application/json_
* Path: `/projects/{pid}`
* Body: [Example](/docs/representations/input/ApplicationJson/project.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  409   | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/project_conflict.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PUTs](#puts)

### Update project issue

#### 🠞 Request

* Method: PUT
* Content-Type: _application/json_
* Path: `/projects/{pid}/issue/{iid}`
* Body: [Example](/docs/representations/input/ApplicationJson/issue.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|        | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/issue_not_found.json)      |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PUTs](#puts)

### Update project issue comment

#### 🠞 Request

* Method: PUT
* Content-Type: _application/json_
* Path: `/projects/{pid}/issue/{iid}/comments/{cid}`
* Body: [Example](/docs/representations/input/ApplicationJson/issue_comment.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|        | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/issue_not_found.json)      |
|        | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/comment_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PUTs](#puts)

___

## **PATCHes**

* [Update project name](#update-project-name)
* [Update project description](#update-project-description)
* [Update project initial state](#update-project-initial-state)
* [Add project state](#add-project-state)
* [Remove project state](#remove-project-state)
* [Add project member](#add-project-member)
* [Remove project member](#remove-project-member)
* [Add project label](#add-project-label)
* [Remove project label](#remove-project-label)
* [Update project issue name](#update-project-issue-name)
* [Update project issue description](#update-project-issue-description)
* [Update project issue state](#update-project-issue-state)
* [Add project issue label](#add-project-issue-label)
* [Remove project issue label](#remove-project-issue-label)

### Update project name

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json_
* Path: `/projects/{pid}/name`
* Body: [Example](/docs/representations/input/ApplicationJson/project_name.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  409   | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/project_conflict.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Update project description

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json_
* Path: `/projects/{pid}/description`
* Body: [Example](/docs/representations/input/ApplicationJson/project_description.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Update project initial state

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json_
* Path: `/projects/{pid}/initialState`
* Body: [Example](/docs/representations/input/ApplicationJson/project_state.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Add project state

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/states`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectStates.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Remove project state

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/states`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectStates.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Add project member

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/members`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectMembers.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Remove project member

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/members`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectMembers.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Add project label

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/labels`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectLabels.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Remove project label

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/labels`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectLabels.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Update project issue name

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json_
* Path: `/projects/{pid}/issue/{iid}/name`
* Body: [Example](/docs/representations/input/ApplicationJson/issue_name.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|        | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/issue_not_found.json)      |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Update project issue description

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json_
* Path: `/projects/{pid}/issue/{iid}/description`
* Body: [Example](/docs/representations/input/ApplicationJson/issue_description.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|        | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/issue_not_found.json)      |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Update project issue state

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json_
* Path: `/projects/{pid}/issue/{iid}/state`
* Body: [Example](/docs/representations/input/ApplicationJson/issue_state.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  400   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/invalid_next_state.json)    |
|        | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/invalid_transition.json)    |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|        | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/issue_not_found.json)      |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Add project issue label

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/issues/{iid}/labels`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectLabels.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

### Remove project issue label

#### 🠞 Request

* Method: PATCH
* Content-Type: _application/json-patch+json_
* Path: `/projects/{pid}/labels`
* Body: [Example](/docs/representations/input/AplicationJsonPatch/PATCH_ProjectLabels.json)

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  404   | _application/problem+json_ |    [Example](/docs/representations/output/ProblemJson/project_not_found.json)     |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[PATCHes](#patches)

___

## **DELETEs**

* [Delete project](#delete-project)
* [Delete project issue](#delete-project-issue)
* [Delete project issue comment](#delete-project-issue-comment)
* [Delete project transition](#delete-project-transition)

### Delete project

#### 🠞 Request

* Method: DELETE
* Path: `/projects/{pid}`

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

### Delete project issue

#### 🠞 Request

* Method: DELETE
* Path: `/projects/{pid}/issues/{iid}`

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[DELETEs](#deletes)

### Delete project issue comment

#### 🠞 Request

* Method: DELETE
* Path: `/projects/{pid}/issues/{iid}/comment/{cid}`

#### 🠞 Response

| Status |        Content-Type        |                                       Body                                        |
| :----: | :------------------------: | :-------------------------------------------------------------------------------: |
|  204   |             -              |                                         -                                         |
|  401   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)   |
|  403   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json) |
|  503   | _application/problem+json_ |  [Example](/docs/representations/output/ProblemJson/database_access_error.json)   |

[DELETEs](#deletes)

### Delete project transition

#### 🠞 Request

* Method: DELETE
* Path: `/projects/{pid}/transition/{tid}`

#### 🠞 Response

| Status |        Content-Type        |                                          Body                                          |
| :----: | :------------------------: | :------------------------------------------------------------------------------------: |
|  204   |             -              |                                           -                                            |
|  400   | _application/problem+json_ | [Example](/docs/representations/output/ProblemJson/delete_transition_bad_request.json) |
|  401   | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/unauthorized_request.json)      |
|  403   | _application/problem+json_ |   [Example](/docs/representations/output/ProblemJson/update_project_forbidden.json)    |
|  503   | _application/problem+json_ |     [Example](/docs/representations/output/ProblemJson/database_access_error.json)     |

[DELETEs](#deletes)