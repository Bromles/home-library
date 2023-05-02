import {User} from "./user";

export {};

declare global {
  namespace Cypress {
    interface Chainable {
      login(user: User): Chainable<JQuery>

      logout(): Chainable<JQuery>

      openRegisterForm(): Chainable<JQuery>

      createUser(user: User): Chainable<JQuery>
    }
  }
}

Cypress.Commands.add('openRegisterForm', () => {
  cy.contains('Библиотека')
  cy.get('[id=loginLink]').click()
  cy.location('pathname').should('contain', '/realms/home_library/protocol/openid-connect')
  cy.get('span > a').click().wait(100)
  cy.location('pathname').should('contain', '/realms/home_library/login-actions/registration')
  cy.get('.pf-c-dropdown__menu-item').first().click( {force: true})
})

Cypress.Commands.add('createUser', (user: User) => {
  cy.openRegisterForm()
  cy.get('#firstName').type(user.username)
  cy.get('#lastName').type('lastname')
  let r = (Math.random() + 1).toString(36).substring(7);
  cy.get('#email').type(r + '@mail.ru')
  cy.get('#username').type(user.username)
  cy.get('#password').type(user.password)
  cy.get('#password-confirm').type(user.password)
  cy.get('.pf-c-button').click().wait(100)
})


Cypress.Commands.add('login', (user:User) => {
  cy.contains('Библиотека')
  cy.get('#loginLink').click().wait(5000)
  cy.location('pathname').should('contain', '/realms/home_library/protocol/openid-connect')
  cy.get('[id=username]').type(user.username)
  cy.get('[id=password]').type(user.password)
  cy.get('[id=kc-login]').click().wait(1000)
})

Cypress.Commands.add('logout', () => {
  cy.contains('Выйти')
  cy.contains('Добавить книгу')
  cy.get('[id=logoutLink]').click().wait(1000)
})