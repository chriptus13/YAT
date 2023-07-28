import React from 'react'
import Button from 'react-bootstrap/Button'
import Spinner from 'react-bootstrap/Spinner'

export default class extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            clicked: false
        }
    }

    render() {
        if (this.state.clicked)
            return this.renderClicked()
        return this.renderNotClicked()
    }

    renderClicked() {
        return (
            <Button variant="outline-dark" disabled>
                <Spinner
                    animation="grow"
                    size="sm"
                    role="status"
                />
                Loading...
            </Button>
        )
    }

    renderNotClicked() {
        return (
            <Button variant={this.props.variant ? this.props.variant : 'outline-dark'} type='submit' onClick={this.handleOnClick}>
                {this.props.text}
            </Button>
        )
    }

    handleOnClick = async ev => {
        ev.preventDefault()
        this.setState({ clicked: true })
        try {
            const res = await fetch(this.props.request())
            let json = {}
            if (res.status == 200)
                json = await res.json()
            this.setState({ clicked: false })
            this.props.onResult(res, json)
        } catch (error) {
            this.setState({ clicked: false })
            this.props.onError(error)
        }
    }
}