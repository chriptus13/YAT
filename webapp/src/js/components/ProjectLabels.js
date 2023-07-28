import React from 'react'
import { API, buildProjectLabelsRequest } from '../services/Hateoas'
import { auth } from '../services/Auth'
import JPatchPage from './templates/JPatchPage';

export default ({ pid }) => {
    return (
        <JPatchPage
            title='Labels'
            addAction={API.actions.add_project_label}
            delAction={API.actions.remove_project_label}
            request={buildProjectLabelsRequest(auth.user, pid)}
            entityField='name'
        />
    )
}