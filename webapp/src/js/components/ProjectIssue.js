import React from 'react'
import { Route } from 'react-router-dom'
import Toolbar from './toolbars/IssueToolbar'
import IssueInfo from './IssueInfo'
import IssueLabels from './IssueLabels'
import IssueComments from './IssueComments'
import Comment from './Comment'

export default ({ pid, iid }) => (
    <div>
        <Toolbar pid={pid} iid={iid} />
        <Route
            exact path='/projects/:pid/issues/:iid'
            render={() => <IssueInfo pid={pid} iid={iid} />}
        />
        <Route
            exact path='/projects/:pid/issues/:iid/labels'
            render={() => <IssueLabels pid={pid} iid={iid} />}
        />
        <Route
            exact path='/projects/:pid/issues/:iid/comments'
            render={() => <IssueComments pid={pid} iid={iid} />}
        />
        <Route
            exact path='/projects/:pid/issues/:iid/comments/:cid'
            render={({ match }) => <Comment pid={pid} iid={iid} cid={match.params.cid} />}
        />
    </div>
)