import React from 'react'
import { Route, Redirect } from 'react-router-dom'
import { auth } from '../services/Auth'

export default function ({ component: Component, render, ...rest }) {
    return (
        <Route {...rest}
            render={props =>
                auth.isAuthenticated
                    ? Component ? <Component {...props} /> : render(props)
                    : <Redirect
                        to={{
                            pathname: "/login",
                            state: { from: props.location }
                        }}
                    />
            }
        />
    )
}