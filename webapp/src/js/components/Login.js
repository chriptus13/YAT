import React from 'react'
import { Redirect } from 'react-router-dom'
import { authenticate } from '../services/Auth'
import Form from 'react-bootstrap/Form'
import Alert from 'react-bootstrap/Alert'
import FetcherButton from './FetcherButton'
import { buildProjectsRequest } from '../services/Hateoas'

const LoginState = {
    unlogged: 'unlogged',
    logged: 'logged',
}

export default class extends React.Component {
    constructor(props) {
        super(props)
        this.user = {
            username: '',
            password: ''
        }
        this.state = {
            loginState: LoginState.unlogged
        }
    }

    render() {
        switch (this.state.loginState) {
            case LoginState.unlogged: return this.renderUnlogged()
            case LoginState.logged: return this.renderLogged()
        }
    }

    renderUnlogged() {
        return (
            <>
                <br />
                {this.state.error ? <Alert variant='danger'>{this.state.error}</Alert> : null}

                <h3>Sign in</h3>
                <Form>
                    <Form.Group>
                        <Form.Label>Username</Form.Label>
                        <Form.Control placeholder='Enter your username' name='username'
                            onChange={this.handleOnChange} />
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password</Form.Label>
                        <Form.Control type='password' placeholder='Enter your password'
                            name='password' onChange={this.handleOnChange} />
                    </Form.Group>
                    <FetcherButton request={this.request.bind(this)} onResult={this.onResult.bind(this)} 
                    onError={this.onError.bind(this)} text='Enter' />
                </Form>
            </>
        )
    }

    renderLogged() {
        const { from } = this.props.location.state || { from: { pathname: "/" } }
        return <Redirect to={from} />
    }

    handleOnChange = ev => {
        ev.preventDefault()
        this.user[ev.target.name] = ev.target.value
    }

    request() {
        const { username, password } = this.user
        return buildProjectsRequest({ username, password })
    }

    onResult(res) {
        if (res.status === 200) {
            authenticate(this.user.username, this.user.password)
            this.setState({ loginState: LoginState.logged })
        } else this.onError()
    }

    onError() {
        this.setState({ error: 'Invalid Credentials!' })
    }
}