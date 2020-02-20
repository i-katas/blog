import React from 'react';

function Result(props) {
    return new React.Component(props)
}

test('jest', () => {
    let result = <Result status='pass'/>

    expect(result.props.status).toBe("pass")
})
