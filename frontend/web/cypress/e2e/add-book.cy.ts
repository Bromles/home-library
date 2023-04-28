describe('Add book', () => {
  beforeEach('Login', () => {
    cy.visit('/')
    cy.login()
  })

  it('should open form', () => {
    cy.get('[id=addBook]').click().wait(100)
    cy.location('pathname').should('eq', '/add-book')
  })
})