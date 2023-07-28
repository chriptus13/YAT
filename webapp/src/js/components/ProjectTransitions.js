import React from 'react'
import { Link } from 'react-router-dom'
import Fetcher from './Fetcher'
import { auth } from '../services/Auth'
import { findAction, capitalize } from '../util/utils'
import { buildRequestFromAction } from '../services/Hateoas'

import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import FetcherButton from './FetcherButton';
import CardColumns from 'react-bootstrap/CardColumns'
import Alert from 'react-bootstrap/Alert'
import { buildProjectTransitionsRequest, API } from '../services/Hateoas';

export default class extends React.Component {
    constructor(props) {
        super(props)
        this.itemToCreate = {}
        this.onResult = this.onResult.bind(this)
        this.onError = this.onError.bind(this)
        this.renderLoaded = this.renderLoaded.bind(this)
        this.renderError = this.renderError.bind(this)
        this.renderLoading = this.renderLoading.bind(this)
        this.state = {
            modalShow: false
        }
    }

    handleClose = () => {
        this.setState({ modalShow: false, error: null, modalError: null });
    }

    handleShow = () => {
        this.setState({ modalShow: true });
    }

    handleOnChange = (ev) => {
        ev.preventDefault()
        this.itemToCreate[ev.target.name] = ev.target.value
    }

    render() {
        if (this.state.modalShow)
            return this.renderLoaded(this.json)

        return (
            <Fetcher request={buildProjectTransitionsRequest(auth.user, this.props.pid)}
                onLoading={this.renderLoading}
                onLoaded={this.renderLoaded}
                onError={this.renderError}
            />
        )
    }

    renderLoading() {
        return <h1>Loading...</h1>
    }

    renderError(error) {
        return <h1>{error.message}!</h1>
    }

    renderLoaded(json) {
        this.json = json //store last response
        const { actions, entities } = json
        const addAction = findAction(actions, API.actions.add_project_transition)
        console.log(API.actions.remove_project_transition)
        return (
            <>
                <br />
                <h1>Transitions</h1>
                <br />
                {this.state.error ? <Alert variant='danger'>{this.state.error}</Alert> : null}
                <Button variant='outline-dark' onClick={this.handleShow}>
                    New
                </Button>
                <br />
                <Modal show={this.state.modalShow} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{addAction.title}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.modalError ? <Alert variant='danger'>{this.state.modalError}</Alert> : null}
                        <Form>
                            {addAction.fields.map(field => {
                                return (
                                    <Form.Group key={field.name}>
                                        <Form.Label>{capitalize(field.name)}</Form.Label>
                                        <Form.Control name={field.name}
                                            onChange={this.handleOnChange} />
                                    </Form.Group>
                                )
                            })}
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <FetcherButton
                            text='Create'
                            request={this.request.bind(this, addAction)}
                            onResult={this.onResult}
                            onError={this.onError}
                        />
                    </Modal.Footer>
                </Modal>
                <CardColumns>
                    {entities.map((entity, index) => {
                        return (
                            <Card key={index} bg='dark' text='white'>
                                <Card.Body>
                                    <Card.Text>{entity.properties.startState} > {entity.properties.endState}</Card.Text>
                                </Card.Body>
                            </Card>
                        )
                    })
                    }
                </CardColumns>
            </>
        )
    }

    request(action) {
        return buildRequestFromAction(auth.user, action, this.itemToCreate)
    }

    onError(error) {
        if (error.detail)
            this.setState({ modalError: error.detail })
        else this.setState({ modalError: 'Error!' })
    }

    async onResult(res) {
        if (res.status == 201)
            return this.handleClose()
        this.onError(await res.json())
    }
}