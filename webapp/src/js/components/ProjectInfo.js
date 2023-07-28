import React from 'react'
import { Redirect } from 'react-router-dom'
import Fetcher from './Fetcher'
import { API } from '../services/Hateoas'
import { auth } from '../services/Auth'
import { findAction, capitalize } from '../util/utils'
import { buildProjectRequest, buildRequestFromAction } from '../services/Hateoas'

import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import FetcherButton from './FetcherButton';
import Alert from 'react-bootstrap/Alert'

const ModalState = {
    editName: 'editName',
    editDescription: 'editDescription',
    editInitialState: 'editInitialState',
    hide: 'hide'
}

export default class extends React.Component {
    constructor(props) {
        super(props)

        this.updateProject = {}

        this.onResultDeleteBtn = this.onResultDeleteBtn.bind(this)
        this.onErrorDeleteBtn = this.onErrorDeleteBtn.bind(this)

        this.onResultModal = this.onResultModal.bind(this)
        this.onErrorModal = this.onErrorModal.bind(this)

        this.renderLoaded = this.renderLoaded.bind(this)
        this.renderError = this.renderError.bind(this)
        this.renderLoading = this.renderLoading.bind(this)

        this.state = {
            modalState: ModalState.hide
        }
    }

    handleClose = () => {
        this.setState({ modalState: ModalState.hide, error: null, modalError: null });
    }

    handleShow = (newState) => {
        this.setState({ modalState: newState });
    }

    handleOnChange = (ev) => {
        ev.preventDefault()
        this.updateProject = {}
        this.updateProject[ev.target.name] = ev.target.value
    }

    render() {
        if (this.state.modalState !== ModalState.hide)
            return this.renderLoaded(this.json)
        if (this.state.deleted)
            return (<Redirect to='/projects' />)

        return (
            <Fetcher request={buildProjectRequest(auth.user, this.props.pid)}
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
        const { name, description, initialState } = json.properties

        const deleteAction = findAction(json.actions, API.actions.delete_project)
        const editNameAction = findAction(json.actions, API.actions.update_project_name)
        const editDescAction = findAction(json.actions, API.actions.update_project_description)
        const editISAction = findAction(json.actions, API.actions.update_project_initialState)
        return (
            <>
                {this.state.error ? <Alert variant='danger'>{this.state.error}</Alert> : null}
                <div className='text-right'>
                    <FetcherButton text='X' request={this.request.bind(this, deleteAction)}
                        onError={this.onErrorDeleteBtn} onResult={this.onResultDeleteBtn} />
                </div>
                {this.renderModals(this.getActionToShow(editNameAction, editDescAction, editISAction))}
                <br />
                <div className='text-center'>
                    <h2>
                        {name}
                        <Button variant='outline-dark' onClick={this.handleShow.bind(this, ModalState.editName)}>
                            Edit
                        </Button>
                    </h2>
                    <span>
                        {description !== 'null' ? description : null}
                        <Button variant='outline-dark' onClick={this.handleShow.bind(this, ModalState.editDescription)}>
                            Edit
                        </Button>
                    </span>
                    <h6>
                        {initialState ? `All issues of this project start with ${initialState} as initial state.` : null}
                        <Button variant='outline-dark' onClick={this.handleShow.bind(this, ModalState.editInitialState)}>
                            Edit
                        </Button>
                    </h6>
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
        console.log(res)
        if (res.status === 204)
            return this.handleClose()
        this.onErrorModal(await res.json())
    }

    request(action) {
        return buildRequestFromAction(auth.user, action, this.updateProject)
    }

    renderModals(action) {
        if (!action) return null
        return (
            <Modal show onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{action.title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.modalError ? <Alert variant='danger'>{this.state.modalError}</Alert> : null}
                    <Form>
                        {action.fields.map(field => {
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
                    <FetcherButton text='Update' request={this.request.bind(this, action)}
                        onResult={this.onResultModal} onError={this.onErrorModal} />
                </Modal.Footer>
            </Modal>
        )
    }

    getActionToShow(editNameAction, editDescAction, editISAction) {
        switch (this.state.modalState) {
            case ModalState.editName:
                return editNameAction
            case ModalState.editDescription:
                return editDescAction
            case ModalState.editInitialState:
                return editISAction
        }
    }
}