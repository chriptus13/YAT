import React from 'react'

const FetchState = {
    loading: 'loading',
    loaded: 'loaded',
    error: 'error'
}

export default class extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            loadState: FetchState.loading
        }
    }

    render() {
        switch (this.state.loadState) {
            case FetchState.loading: return this.renderLoading()
            case FetchState.loaded: return this.renderLoaded()
            case FetchState.error: return this.renderError()
        }
    }

    renderLoading() {
        return this.props.onLoading()
    }

    renderLoaded() {
        return this.props.onLoaded(this.state.json)
    }

    renderError() {
        return this.props.onError(this.state.error)
    }

    componentDidMount() {
        this.cancellationToken = new CancellationToken()
        this.load(this.props.request, this.cancellationToken)
    }

    componentDidUpdate(prevProps, prevState) {
        
        if (prevState.loadState === FetchState.loaded) {
            this.cancellationToken.tryCancel()
            this.cancellationToken = new CancellationToken()
            this.setState({ loadState: FetchState.loading })
            this.load(this.props.request, this.cancellationToken)
        }
        if (this.props.url !== prevProps.url) {
            this.cancellationToken.tryCancel()
            this.cancellationToken = new CancellationToken()
            this.setState({
                loadState: this.state.loadState === FetchState.loaded //TODO check reload
                    ? FetchState.reloading
                    : FetchState.loading
            })
            this.load(this.props.request, this.cancellationToken)
        }
    }

    componentWillUnmount() {
        this.cancellationToken.tryCancel()
    }

    async load(request, ctk) {
        try {
            let res = await fetch(request)
            if (ctk.isCancelled) return
            if (res.status === 200) {
                let json = await res.json()
                if (ctk.isCancelled) return
                this.setState({
                    loadState: FetchState.loaded,
                    json: json
                })
            } else {
                this.setState({
                    loadState: FetchState.error,
                    error: { message: `non-200 status (${res.status})` }
                })
            }
        } catch (error) {
            if (ctk.isCancelled) return
            this.setState({
                loadState: FetchState.error,
                error: error
            })
        }
    }
}

class CancellationToken {
    constructor() {
        this.isCancelled = false
    }

    tryCancel() {
        if (this.isCancelled) return false
        this.isCancelled = true
        return true
    }
}