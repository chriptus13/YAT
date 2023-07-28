import { UserManager } from 'oidc-client'

export const auth = {
    isAuthenticated: false,
    user: {}
}

export function authenticate(username, password) {
    auth.isAuthenticated = true
    auth.user = { username, password }
}

export async function signout() {
    auth.isAuthenticated = false
    auth.user = {}
    //await userManager.signoutRedirect()
    await userManager.clearStaleState()
}

const settings = {
    authority: 'http://35.246.2.231/openid-connect-server-webapp',
    client_id: 'yat-client',
    redirect_uri: 'http://35.197.250.227/index.html',
    popup_redirect_uri: 'http://35.197.250.227/index.html',
    response_type: 'code',
    scope: 'openid profile',
    loadUserInfo: true
}

export const userManager = new UserManager(settings)

export function getBasicAuthorization() {
    return 'Basic ' + btoa(`${auth.user.username}:${auth.user.password}`)
}

export function getBearerAuthorization() {
    return 'Bearer ' + auth.user.access_token
}