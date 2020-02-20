module.exports = {
    moduleDirectories: ['src', 'node_modules'],
    setupFilesAfterEnv: ['jest-enzyme'],
    testEnvironment: 'enzyme',
    testEnvironmentOptions: {
        enzymeAdapter: 'react16'
    }
}
