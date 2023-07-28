import React from 'react'
import Dropdown from 'react-bootstrap/Dropdown'
import { Link } from 'react-router-dom'

export default ({ pid }) => {
    return (
        <>
            <br />
            <Dropdown>
                <Dropdown.Toggle variant='dark' id='project-dropdown'>Project</Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item as={Link} to={`/projects/${pid}`}>Info</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/issues`}>Issues</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/states`}>States</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/transitions`}>Transitions</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/labels`}>Labels</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/members`}>Members</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        </>
    )
}