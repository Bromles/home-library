describe('Login and Logout', () => {
  it('Login', () => {
    cy.visit('/')
    cy.login()
  })

  it('Logout', () => {
    cy.visit('/')
    cy.login()
    cy.logout()
  })
})
