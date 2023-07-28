import React from 'react'
import { Link } from 'react-router-dom'
import Fetcher from '../Fetcher'
import { auth } from '../../services/Auth'
import { findAction, capitalize } from '../../util/utils'
import { buildRequestFromAction } from '../../services/Hateoas'

import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import FetcherButton from '../FetcherButton';
import CardColumns from 'react-bootstrap/CardColumns'
import Alert from 'react-bootstrap/Alert'

/**
 * Props:
 * request - request that fetcher will do when rendering the component
 * createAction - name of action
 * title
 * entityField
 * getUriTo - function that return uri to entity
 * getEntityId - id used for key in CardCollumn
 */
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
        this.setState({ modalShow: false, error: null });
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
            <Fetcher request={this.props.request}
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
        const createAction = findAction(actions, this.props.createAction)
        return (
            <>
                <br />
                <h1>{this.props.title}</h1>
                <br />
                <Button variant='outline-dark' onClick={this.handleShow}>
                    New
                </Button>
                <br />
                <Modal show={this.state.modalShow} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{createAction.title}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.error ? <Alert variant='danger'>{this.state.error}</Alert> : null}
                        <Form>
                            {createAction.fields.map(field => {
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
                            request={this.request.bind(this, createAction)} 
                            onResult={this.onResult} 
                            onError={this.onError} 
                        />
                    </Modal.Footer>
                </Modal>
                <CardColumns>
                    {entities.map(entity => {
                        const name = entity.properties[this.props.entityField]
                        return (
                            <Card key={this.props.getEntityId(entity)} bg='dark' text='white'>
                                <Card.Body>
                                    <Card.Text>{name}</Card.Text>
                                    <Button as={Link} to={this.props.getUriTo(entity)} variant='light'>Details</Button>
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
            this.setState({ error: error.detail })
        else this.setState({ error: 'Error!' })
    }

    async onResult(res) {
        console.log(res)
        if (res.status == 201)
            return this.handleClose()
        this.onError(await res.json())
    }
}