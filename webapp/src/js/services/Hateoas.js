import { expandTemplate } from '../util/utils'
import { getBearerAuthorization as getAuthorization } from './Auth'

export const API = {
    host: 'http://35.197.250.227/api',
    relations: {
        /* ** DEFAULT ** */

        self: 'self',
        collection: 'collection',
        item: 'item',
        parent: 'parent',

        /* ** Domain ** */

        projects: '/rels/projects',
        project: '/rels/project',
        issues: '/rels/project/issues',
        issue: '/rels/project/issue',
        comments: '/rels/project/issue/comments',
        states: '/rels/project/states',
        project_labels: '/rels/project/labels',
        issue_labels: '/rels/project/issue/labels',
        transitions: '/rels/project/transitions',
        members: '/rels/project/members',
        comment: '/rels/project/issue/comment'
    },
    actions: {
        create_project: '/actions/create/project',
        delete_project: '/actions/delete/project',
        update_project_name: '/actions/update/project/name',
        update_project_description: '/actions/update/project/description',
        update_project_initialState: '/actions/update/project/initialState',
        add_project_state: '/actions/add/project/state',
        add_project_label: '/actions/add/project/label',
        remove_project_state: '/actions/remove/project/state',
        remove_project_label: '/actions/remove/project/label',
        add_project_member: '/actions/add/project/member',
        remove_project_member: '/actions/remove/project/member',
        create_issue: '/actions/create/issue',
        add_issue_label: '/actions/add/issue/label',
        remove_issue_label: '/actions/remove/issue/label',
        update_issue_name: '/actions/update/issue/name',
        update_issue_description: '/actions/update/issue/description',
        update_issue_state: '/actions/update/issue/state',
        delete_issue: '/actions/delete/issue',
        add_issue_comment: '/actions/create/comment',
        delete_comment: '/actions/delete/comment',
        update_comment: '/actions/update/comment',
        add_project_transition: '/actions/create/transition',
        remove_project_transition: '/actions/delete/transition'
    },
    homeDoc: {}
}

export async function loadHomeDocument() {
    const resp = await fetch(API.host)
    if (resp.ok) API.homeDoc = await resp.json()
    else throw new Error(`Failed on getting Home Document [status - ${resp.status}]`)
}

function buildGetRequest(uri) {
    return new Request(uri, {
        'method': 'GET',
        'headers': {
            'Accept': 'application/vnd.siren+json',
            'Accept-Charset': 'utf-8',
            'Authorization': getAuthorization()
        }
    })
}

export function buildProjectsRequest(user) {
    return buildGetRequest(API.host + API.homeDoc.resources[API.relations.projects].href, user)
}

export function buildProjectRequest(user, pid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.project], { pid })
    return buildGetRequest(API.host + path, user)
}

export function buildProjectStatesRequest(user, pid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.states], { pid })
    return buildGetRequest(API.host + path, user)
}

export function buildProjectMembersRequest(user, pid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.members], { pid })
    return buildGetRequest(API.host + path, user)
}

export function buildProjectLabelsRequest(user, pid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.project_labels], { pid })
    return buildGetRequest(API.host + path, user)
}

export function buildProjectTransitionsRequest(user, pid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.transitions], { pid })
    return buildGetRequest(API.host + path, user)
}

export function buildIssuesRequest(user, pid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.issues], { pid })
    return buildGetRequest(API.host + path, user)
}

export function buildIssueRequest(user, pid, iid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.issue], { pid, iid })
    return buildGetRequest(API.host + path, user)
}

export function buildIssueLabelsRequest(user, pid, iid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.issue_labels], { pid, iid })
    return buildGetRequest(API.host + path, user)
}

export function buildCommentsRequest(user, pid, iid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.comments], { pid, iid })
    return buildGetRequest(API.host + path, user)
}

export function buildCommentRequest(user, pid, iid, cid) {
    const path = expandTemplate(API.homeDoc.resources[API.relations.comment], { pid, iid, cid })
    return buildGetRequest(API.host + path, user)
}

export function buildRequestFromAction(user, action, body) {
    return new Request(
        API.host + action.href, {
            'method': action.method,
            'headers': {
                'Content-Type': action.type,
                'Authorization': getAuthorization()
            },
            'body': JSON.stringify(body)
        }
    )
} 