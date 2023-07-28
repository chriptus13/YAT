import React from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import Spinner from 'react-bootstrap/Spinner'
import { loadHomeDocument } from '../services/Hateoas'
import Home from './Home'
import Login from './Login-oidc'
import Container from 'react-bootstrap/Container'

import Navbar from './MainNavbar.js'
import Projects from './Projects';
import PrivateRoute from './PrivateRoute';
import Project from './Project'


const LoadState = {
    loading: 'loading',
    loaded: 'loaded',
    error: 'error'
}

export default class extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            loadState: LoadState.loading
        }
    }

    async componentDidMount() {
        try {
            await loadHomeDocument()
            this.setState({ loadState: LoadState.loaded })
        } catch (error) {
            this.setState({ loadState: LoadState.error, error })
        }
    }

    render() {
        switch (this.state.loadState) {
            case LoadState.loading: return this.renderLoading()
            case LoadState.loaded: return this.renderLoaded()
            case LoadState.error: return this.renderError()
        }
    }

    renderLoading() {
        return <Spinner />
    }

    renderLoaded() {
        return (
            <Router>
                <Navbar />
                <Container>
                    <Route exact path='/' component={Home} />
                    <Route exact path='/login' component={Login} />
                    <PrivateRoute exact path='/projects' component={Projects} />
                    <PrivateRoute path='/projects/:pid'
                        render={
                            ({ match }) => <Project pid={match.params.pid} />
                        }
                    />
                </Container>
            </Router>
        )
    }

    renderError() {
        return (
            <h1>{this.state.error.message}</h1>
        )
    }
}