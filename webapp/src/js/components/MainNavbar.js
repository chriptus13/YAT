import React from 'react'
import Navbar from 'react-bootstrap/Navbar'
import Nav from 'react-bootstrap/Nav'
import { withRouter, Link } from 'react-router-dom'
import { auth, signout } from '../services/Auth'

export default withRouter(MainNavbar)

function MainNavbar() {
    return (
        <>
            <Navbar bg='dark' variant='dark'>
                <Navbar.Brand><img src='/favicon.ico' width='50' height='50' className='d-inline-block align-top' /></Navbar.Brand>
                <Nav>
                    <Nav.Link as={Link} to='/' active={location.pathname === '/'}>Home</Nav.Link>

                    {auth.isAuthenticated ?
                        <>
                            <Nav.Link as={Link} to='/projects' active={location.pathname.startsWith('/projects')}>
                                Projects
                            </Nav.Link>
                            <Nav.Link as={Link} to='/' onClick={signout}>Logout</Nav.Link>
                        </>
                        : <Nav.Link as={Link} to='/login' active={location.pathname === '/login'}>Login</Nav.Link>
                    }
                </Nav>
            </Navbar>
        </>
    )
}