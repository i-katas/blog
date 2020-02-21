import React from 'react'

export default class extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {loaded: false};
    }

    componentDidMount() {
        this.props.items.then(() => this.setState({loaded: true}))
    }

    render() {
        let {loaded} = this.state;

        return (
            <ul id='blogs'>{loaded && <li className='empty'>No blogs</li>}</ul>
        )
    }
}