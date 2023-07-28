import React from 'react'
import { API, buildProjectStatesRequest } from '../services/Hateoas'
import { auth } from '../services/Auth'
import JPatchPage from './templates/JPatchPage';

export default ({ pid }) => {
    return (
        <JPatchPage
            title='States'
            addAction={API.actions.add_project_state}
            delAction={API.actions.remove_project_state}
            request={buildProjectStatesRequest(auth.user, pid)}
            entityField='name'
        />
    )
}