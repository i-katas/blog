import BlogList from './BlogList'
import React from 'react'
import ReactDOM from 'react-dom'

const services = {
    host: process.env.SERVICES_HOST || 'localhost',
    port: process.env.SERVICES_PORT || 9090
};
ReactDOM.render(<BlogList items={fetch(`http://${services.host}:${services.port}/blogs`)}/>, document.getElementById('content'));


