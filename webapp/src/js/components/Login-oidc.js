import React from 'react'
import { Redirect } from 'react-router-dom'
import { auth, userManager } from '../services/Auth'

export default class extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            authenticated: false
        }
    }

    render() {
        if (!this.state.authenticated)
            return (
                <div>
                    <button onClick={this.handleAuthentication} >Login with MITREid</button>
                </div>
            )
        const { from } = this.props.location.state || { from: { pathname: "/" } }
        return <Redirect to={from} />
    }

    handleAuthentication = async () => {
        let user = await userManager.getUser()
        if (!user) {
            try {
                user = await userManager.signinPopup()
            } catch (error) {
                console.log(error)
                this.setState({
                    error: error
                })
                return
            }
        }
        this.setState({
            authenticated: true,
            user: user
        })
        auth.user = user
        auth.isAuthenticated = true
    }
}