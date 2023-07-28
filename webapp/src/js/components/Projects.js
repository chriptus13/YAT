import React from 'react'
import { API } from '../services/Hateoas'
import { auth } from '../services/Auth'
import { extractUriParams } from '../util/utils'
import { buildProjectsRequest } from '../services/Hateoas'
import ItemsListPage from './templates/ItemsListPage';

export default () => {
    return (
        <ItemsListPage
            title='Projects'
            createAction={API.actions.create_project}
            request={buildProjectsRequest(auth.user)}
            entityField='name'
            getUriTo={getUriToProject}
            getEntityId={getProjectId}
        />
    )
}

function getProjectId(project) {
    return getUriParams(project).pid
}

function getUriToProject(project) {
    const {pid} = getUriParams(project)
    return `/projects/${pid}`
}

function getUriParams(project) {
    const href = project.links.find(link => link.rel.includes(API.relations.self)).href
    return extractUriParams(
        API.homeDoc.resources[API.relations.project].hrefTemplate,
        href
    )
}