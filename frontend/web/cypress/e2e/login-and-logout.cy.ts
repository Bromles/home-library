import {User} from "../support/user";

describe('Login and Logout', () => {
  beforeEach('', () => {
    cy.visit('/')
  })

  it('Failed auth', () => {
    cy.openRegisterForm()
    cy.get('.pf-c-button').click().wait(100)
    cy.get('#input-error-firstname').should('contain', 'имя')
    cy.get('#input-error-lastname').should('contain', 'фамилию')
    cy.get('#input-error-email').should('contain', 'E-mail')
    cy.get('#input-error-username').should('contain', 'имя пользователя')
    cy.get('#input-error-password').should('contain', 'пароль')
  })
  //
  // it('Success auth', () => {
  //   cy.createUser(new User('auth', 'auth'))
  //   cy.location('pathname').should('eq', '/books')
  // })
  //
  // it('Failed auth (user already exist)', () => {
  //   cy.createUser(new User('auth', 'auth'))
  //   cy.get('#input-error-username').should('contain', 'Имя пользователя')
  // })
  //
  // it('Login', () => {
  //   cy.login(new User('auth', 'auth'))
  //   cy.location('pathname').should('eq', '/books')
  //   cy.contains('Выйти')
  //   cy.contains('Добавить книгу')
  // })
  // it('Logout', () => {
  //   cy.login(new User('auth', 'auth'))
  //   cy.logout()
  //
  //   cy.location('pathname').should('eq', '/')
  //   cy.contains('Войти')
  // })
})
