import React from 'react'
import JPatchPage from './templates/JPatchPage'
import { buildIssueLabelsRequest, API } from '../services/Hateoas';
import { auth } from '../services/Auth';

export default ({ pid, iid }) => (
    <JPatchPage
        request={buildIssueLabelsRequest(auth.user, pid, iid)}
        addAction={API.actions.add_issue_label}
        delAction={API.actions.remove_issue_label}
        title='Labels'
        entityField='name'
    />
)