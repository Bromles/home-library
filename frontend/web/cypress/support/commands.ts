export {};

declare global {
  namespace Cypress {
    interface Chainable {
      login(user?: any): Chainable<JQuery>
      logout(): Chainable<JQuery>
    }
  }
}

Cypress.Commands.add('login', (user?) => {
  cy.contains('Библиотека')
  cy.get('[id=loginLink]').click()
  cy.location('pathname').should('contain', '/realms/home_library/protocol/openid-connect')
  cy.get('[id=username]').type('test')
  cy.get('[id=password]').type('test')
  cy.get('[id=kc-login]').click().wait(1000)
  cy.location('pathname').should('eq', '/books')
  cy.contains('Выйти')
  cy.contains('Добавить книгу')
})

Cypress.Commands.add('logout', () => {
  cy.contains('Выйти')
  cy.contains('Добавить книгу')
  cy.get('[id=logoutLink]').click().wait(1000)
  cy.location('pathname').should('eq', '/')
  cy.contains('Войти')
})