import React from 'react'
import ReactDOM from 'react-dom'
import App from './js/components/App'
import style from 'bootstrap/dist/css/bootstrap.css'
import { userManager } from './js/services/Auth'

userManager.signinPopupCallback()

ReactDOM.render(
    <App />,
    document.getElementById('root')
)