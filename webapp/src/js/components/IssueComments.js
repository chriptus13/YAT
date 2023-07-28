import React from 'react'
import Fetcher from './Fetcher'
import { Link } from 'react-router-dom'
import { buildCommentsRequest, buildRequestFromAction, API } from '../services/Hateoas'
import { auth } from '../services/Auth'
import { findAction, capitalize } from '../util/utils'
import Modal from 'react-bootstrap/Modal'
import ListGroup from 'react-bootstrap/ListGroup'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import FetcherButton from './FetcherButton'
import Alert from 'react-bootstrap/Alert'

export default class extends React.Component {
    constructor(props) {
        super(props)

        this.renderLoading = this.renderLoading.bind(this)
        this.renderLoaded = this.renderLoaded.bind(this)
        this.renderError = this.renderError.bind(this)

        this.handleShow = this.handleShow.bind(this)
        this.handleClose = this.handleClose.bind(this)
        this.handleOnChange = this.handleOnChange.bind(this)

        this.comment = {}

        this.onErrorAdd = this.onErrorAdd.bind(this)
        this.onResultAdd = this.onResultAdd.bind(this)

        this.state = {
            modalShow: false
        }
    }

    render() {
        if (this.state.modalShow)
            return this.renderLoaded(this.json)
        return <Fetcher
            request={buildCommentsRequest(auth.user, this.props.pid, this.props.iid)}
            onLoading={this.renderLoading}
            onLoaded={this.renderLoaded}
            onError={this.renderError}
        />
    }

    renderLoading() {
        return <h1>Loading...</h1>
    }

    renderError(error) {
        return <h1>{error}</h1>
    }

    renderLoaded(json) {
        this.json = json
        const { actions, entities } = json
        const addAction = findAction(actions, API.actions.add_issue_comment)
        return (
            <>
                <br />
                <h1>Comments</h1>
                <br />
                <Button variant='outline-dark' onClick={this.handleShow}>
                    Add
                </Button>
                <br />
                <Modal show={this.state.modalShow} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{addAction.title}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.modalError ? <Alert variant='danger'>{this.state.modalError}</Alert> : null}
                        <Form>
                            {addAction.fields.map(field =>
                                <Form.Group key={field.name}>
                                    <Form.Label>{capitalize(field.name)}</Form.Label>
                                    <Form.Control name={field.name} onChange={this.handleOnChange} />
                                </Form.Group>
                            )}

                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <FetcherButton text='Add' request={this.request.bind(this, addAction)}
                            onResult={this.onResultAdd} onError={this.onErrorAdd} />
                    </Modal.Footer>
                </Modal>
                <ListGroup>
                    {entities.map((comment, index) => 
                        <ListGroup.Item action key={index} as={Link} to={getUriToComment(comment)}>
                            <span>
                                <strong>{comment.properties.creator}</strong>
                                ({new Date(comment.properties.created).toDateString()})
                                <br />
                                {comment.properties.text}
                            </span>
                        </ListGroup.Item>
                    )}
                </ListGroup>
            </>
        )
    }

    handleShow() {
        this.setState({ modalShow: true })
    }

    handleClose() {
        this.setState({ modalShow: false, modalError: null })
    }

    handleOnChange(ev) {
        ev.preventDefault()
        this.comment[ev.target.name] = ev.target.value
    }

    async onResultAdd(res) {
        if (res.status == 201)
            return this.handleClose()
        this.onErrorAdd(await res.json())
    }

    onErrorAdd(error) {
        if (error.detail)
            this.setState({ modalError: error.detail })
        else this.setState({ modalError: 'Error!' })
    }

    request(action) {
        return buildRequestFromAction(auth.user, action, this.comment)
    }
}

function getUriToComment(comment) {
    return comment.links.find(link => link.rel.includes(API.relations.self)).href
}