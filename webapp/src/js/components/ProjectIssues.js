import React from 'react'
import { API, buildIssuesRequest } from '../services/Hateoas'
import { auth } from '../services/Auth'
import { extractUriParams } from '../util/utils'
import ItemsListPage from './templates/ItemsListPage';

export default ({ pid }) => {
    return (
        <ItemsListPage
            title='Issues'
            createAction={API.actions.create_issue}
            request={buildIssuesRequest(auth.user, pid)}
            entityField='name'
            getUriTo={getUriToIssue}
            getEntityId={getIssueId}
        />
    )
}

function getIssueId(issue) {
    const params = getUriParams(issue)
    return `${params.pid}_${params.iid}`
}

function getUriToIssue(issue) {
    const params = getUriParams(issue)
    return `/projects/${params.pid}/issues/${params.iid}`
}

function getUriParams(issue) {
    const href = issue.links.find(link => link.rel.includes(API.relations.self)).href
    return extractUriParams(
        API.homeDoc.resources[API.relations.issue].hrefTemplate,
        href
    )
}