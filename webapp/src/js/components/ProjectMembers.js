import React from 'react'
import { API, buildProjectMembersRequest } from '../services/Hateoas'
import { auth } from '../services/Auth'
import JPatchPage from './templates/JPatchPage';

export default ({ pid }) => {
    return (
        <JPatchPage
            title='Members'
            addAction={API.actions.add_project_member}
            delAction={API.actions.remove_project_member}
            request={buildProjectMembersRequest(auth.user, pid)}
            entityField='username'
        />
    )
}