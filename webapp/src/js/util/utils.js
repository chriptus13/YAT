import React from 'react'
const urlReg = /https?:\/\/[^/]+(\/.*)/
const paramReg = /{(\w+)}/

/**
 * Util function to extract params from an URL according to an URI Template.
 * @param {String} uriTemplate - Uri template to test.
 * @param {String} url - URL to match the template and get vars extract according with template.
 */
export function extractUriParams(uriTemplate, url) {
    const templatePath = uriTemplate.indexOf('/') === 0 ? uriTemplate : uriTemplate.match(urlReg)[1]
    const urlPath = url.indexOf('/') === 0 ? url : url.match(urlReg)[1]

    const templateParts = templatePath.split('/')
    const urlParts = urlPath.split('/')

    if (templateParts.length !== urlParts.length
        || templateParts.some((part, idx) => !paramReg.test(part) && part !== urlParts[idx])
    ) return

    const res = {}
    templateParts.forEach((part, idx) => {
        if (paramReg.test(part)) res[part.match(paramReg)[1]] = urlParts[idx]
    })
    return res
}

/**
 * Util function to make requests
 * @param {String} url      - URL for the request
 * @param {String} username - Username for authorization
 * @param {String} password - Password for authorization
 * @param {String} method   - HTTP method
 * @param {Object} body     - Request payload
 */
export function ourFetch(url, username, password, method = 'GET', body) {
    const options = {
        'method': method,
        'headers': {
            'Accept': 'application/vnd.siren+json',
            'Accept-Charset': 'utf-8',
            'Authorization': 'Basic ' + btoa(`${username}:${password}`)
        }
    }
    if (body) {
        options.body = JSON.stringify(body)
        options.headers['Content-Type'] = 'application/json'
    }
    return fetch(url, options)
}

export function expandTemplate({ hrefTemplate, hrefVars }, params) {
    let uri = hrefTemplate
    Object.keys(hrefVars)
        .forEach(param => {
            uri = uri.replace(`{${param}}`, params[param])
        })

    return uri
}

export function showAlert(message, type = 'danger') {
    return (
        <div className={`alert alert-${type} alert-dismissible fade show`} role="alert">
            <button className="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <div>{message}</div>
        </div>
    )
}

export function findAction(sirenActions, actionName) {
    return sirenActions.find(it => it.name === actionName)
}

export function capitalize(string) {
    return string[0].toUpperCase() + string.substring(1)
}