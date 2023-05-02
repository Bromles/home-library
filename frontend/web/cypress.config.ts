import { defineConfig } from 'cypress'

export default defineConfig({
  
  e2e: {
    'baseUrl': 'http://localhost:8123'
  },
  // video: false,
  // screenshotOnRunFailure: false,
  defaultCommandTimeout: 30000,
  
  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },
    specPattern: '**/*.cy.ts'
  }
  
})

module.exports = {
  projectId: "y4e16v",
  // The rest of the Cypress config options go here...
}