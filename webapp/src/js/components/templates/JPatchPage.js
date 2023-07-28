import React from 'react'
import Fetcher from '../Fetcher'
import { auth } from '../../services/Auth'
import { findAction } from '../../util/utils'
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
 * addAction - name of action
 * delAction - name of action
 * title
 * entityField
 */
export default class extends React.Component {
    constructor(props) {
        super(props)
        this.value = ''
        
        this.onResultAdd = this.onResultAdd.bind(this)
        this.onErrorAdd = this.onErrorAdd.bind(this)

        this.onResultDel = this.onResultDel.bind(this)
        this.onErrorDel = this.onErrorDel.bind(this)

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
        this.value = ev.target.value
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
        const addAction = findAction(actions, this.props.addAction)
        const delAction = findAction(actions, this.props.delAction)
        return (
            <>
                <br />
                <h1>{this.props.title}</h1>
                <br />
                {this.state.error ? <Alert variant='danger'>{this.state.error}</Alert> : null}
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
                            <Form.Group>
                                <Form.Label>Name</Form.Label>
                                <Form.Control onChange={this.handleOnChange} />
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <FetcherButton text='Add' request={this.requestAdd.bind(this, addAction)}
                            onResult={this.onResultAdd} onError={this.onErrorAdd} />
                    </Modal.Footer>
                </Modal>
                <CardColumns>
                    {entities.map(entity => {
                        const name = entity.properties[this.props.entityField]
                        return (
                            <Card key={name} bg='dark' text='white'>
                                <Card.Body>
                                    <div className='text-right'>
                                        <FetcherButton text='X' variant='light' 
                                        request={this.requestRemove.bind(this, delAction, name)}
                                        onResult={this.onResultDel} onError={this.onErrorDel}/>
                                    </div>
                                    <Card.Text>{name}</Card.Text>
                                </Card.Body>
                            </Card>
                        )
                    })
                    }
                </CardColumns>
            </>
        )
    }

    requestAdd(action) {
        return buildRequestFromAction(auth.user, action, [{ op: 'add', path: '/', value: this.value }])
    }

    requestRemove(action, value) {
        return buildRequestFromAction(auth.user, action, [{ op: 'remove', path: `/${value}`}])
    }

    onErrorAdd(error) {
        if (error.detail)
            this.setState({ modalError: error.detail })
        else this.setState({ modalError: 'Error!' })
    }

    async onResultAdd(res) {
        if (res.status == 204)
            return this.handleClose()
        this.onErrorAdd(await res.json())
    }

    onErrorDel(error) {
        if (error.detail)
            this.setState({ error: error.detail })
        else this.setState({ error: 'Error!' })
    }

    async onResultDel(res) {
        if (res.status == 204)
            return this.handleClose()
        this.onErrorDel(await res.json())
    }
}