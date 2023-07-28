import React from 'react'
import { Redirect } from 'react-router-dom'
import Fetcher from './Fetcher'
import { API, buildCommentRequest, buildRequestFromAction } from '../services/Hateoas'
import { auth } from '../services/Auth'
import { findAction, capitalize } from '../util/utils'
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'
import Form from 'react-bootstrap/Form'
import FetcherButton from './FetcherButton'
import Alert from 'react-bootstrap/Alert'

export default class extends React.Component {
    constructor(props) {
        super(props)

        this.updateComment = {}

        this.onResultDeleteBtn = this.onResultDeleteBtn.bind(this)
        this.onErrorDeleteBtn = this.onErrorDeleteBtn.bind(this)

        this.onResultModal = this.onResultModal.bind(this)
        this.onErrorModal = this.onErrorModal.bind(this)

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

    handleShow = (modalAction) => {
        this.modalAction = modalAction
        this.setState({ modalShow: true });
    }

    handleOnChange = (ev) => {
        ev.preventDefault()
        this.updateComment = {}
        this.updateComment[ev.target.name] = ev.target.value
    }

    render() {
        if (this.state.modalShow)
            return this.renderLoaded(this.json)
        if (this.state.deleted)
            return (<Redirect to={`/projects/${this.props.pid}/issues/${this.props.iid}/comments`} />)

        return (
            <Fetcher request={buildCommentRequest(auth.user, this.props.pid, this.props.iid, this.props.cid)}
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
        const { text, creator, created } = json.properties

        const deleteAction = findAction(json.actions, API.actions.delete_comment)
        const updateAction = findAction(json.actions, API.actions.update_comment)
        return (
            <>
                {this.state.error ? <Alert variant='danger'>{this.state.error}</Alert> : null}
                <div className='text-right'>
                    <FetcherButton text='X' request={this.request.bind(this, deleteAction)}
                        onError={this.onErrorDeleteBtn} onResult={this.onResultDeleteBtn} />
                </div>
                {this.renderModal()}
                <br />
                <div className='text-center'>
                    <h2>
                        {creator}
                    </h2>
                    <span>
                        {new Date(created).toDateString()}
                        <br />
                        {text}
                        <Button variant='outline-dark' onClick={this.handleShow.bind(this, updateAction)}>
                            Edit
                        </Button>
                    </span>
                </div>
            </>
        )
    }

    onErrorDeleteBtn(error) {
        if (error.detail)
            return this.setState({ error: error.detail })
        this.setState({ error: 'Error!' })
    }

    async onResultDeleteBtn(res) {
        if (res.status === 204)
            return this.setState({ deleted: true })
        this.onErrorDeleteBtn(await res.json())
    }

    onErrorModal(error) {
        if (error.detail)
            return this.setState({ modalError: error.detail })
        this.setState({ modalError: 'Error!' })
    }

    async onResultModal(res) {
        if (res.status === 204)
            return this.handleClose()
        this.onErrorModal(await res.json())
    }

    request(action) {
        return buildRequestFromAction(auth.user, action, this.updateComment)
    }

    renderModal() {
        if (!this.state.modalShow) return null
        return (
            <Modal show onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{this.modalAction.title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.modalError ? <Alert variant='danger'>{this.state.modalError}</Alert> : null}
                    <Form>
                        {this.modalAction.fields.map(field => {
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
                    <FetcherButton text='Update' request={this.request.bind(this, this.modalAction)}
                        onResult={this.onResultModal} onError={this.onErrorModal} />
                </Modal.Footer>
            </Modal>
        )
    }
}