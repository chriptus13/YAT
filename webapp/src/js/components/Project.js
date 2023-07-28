import React from 'react'
import { Route } from 'react-router-dom'

import Toolbar from './toolbars/ProjectToolbar'
import ProjectInfo from './ProjectInfo'
import ProjectStates from './ProjectStates'
import ProjectLabels from './ProjectLabels'
import ProjectMembers from './ProjectMembers'
import ProjectIssues from './ProjectIssues'
import ProjectIssue from './ProjectIssue'
import ProjectTransitions from './ProjectTransitions'

export default ({ pid }) => (
    <div>
        <Toolbar pid={pid} />
        <Route exact path='/projects/:pid'
            render={() => <ProjectInfo pid={pid} />}
        />
        <Route exact path='/projects/:pid/states'
            render={() => <ProjectStates pid={pid} />}
        />
        <Route exact path='/projects/:pid/labels'
            render={() => <ProjectLabels pid={pid} />}
        />
        <Route exact path='/projects/:pid/transitions'
            render={() => <ProjectTransitions pid={pid} />}
        />
        <Route exact path='/projects/:pid/members'
            render={() => <ProjectMembers pid={pid} />}
        />
        <Route exact path='/projects/:pid/issues'
            render={() => <ProjectIssues pid={pid} />}
        />
        <Route path='/projects/:pid/issues/:iid'
            render={({ match }) => <ProjectIssue pid={pid} iid={match.params.iid} />}
        />
    </div>
)