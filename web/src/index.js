import BlogList from './BlogList'
import React from 'react'
import ReactDOM from 'react-dom'

ReactDOM.render(<BlogList items={fetch('http://localhost:8080/blogs')}/>, document.getElementById('content'));


