import React from 'react'
import { Link } from 'react-router-dom'
import Dropdown from 'react-bootstrap/Dropdown'

export default ({ pid, iid }) => {
    return (
        <>
            <br />
            <Dropdown>
                <Dropdown.Toggle variant='dark' id='project-dropdown'>Issue</Dropdown.Toggle>
                <Dropdown.Menu>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/issues/${iid}`}>Info</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/issues/${iid}/labels`}>Labels</Dropdown.Item>
                    <Dropdown.Item as={Link} to={`/projects/${pid}/issues/${iid}/comments`}>Comments</Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        </>
    )
}