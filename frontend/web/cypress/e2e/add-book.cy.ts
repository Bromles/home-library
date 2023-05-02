import {User} from "../support/user";

describe('Add book', () => {

  let user = new User('test', 'test');

  // before('Auth user', () => {
  //   cy.visit('/')
  //   cy.createUser(user)
  // })

  beforeEach('Login', () => {
    cy.visit('/')
    cy.login(user)
    cy.get('#addBook').click().wait(100)
  })

  it('should open form', () => {
    cy.location('pathname').should('eq', '/add-book')
  })

  it('Should create new book', () => {
    cy.get('#name').type('Book Name')
    cy.get('#author').type('Book Author')
    cy.get('input[type=file]').first().selectFile('cypress/fixtures/files/test.pdf', {force: true})
    cy.get('input[type=file]').last().selectFile('cypress/fixtures/images/img.png', {force: true})
    cy.get('#tags > .p-dropdown > .p-dropdown-trigger').click()
    cy.get('[ng-reflect-label="tag1"] > .p-ripple').click()
    cy.get('#categories > .p-dropdown > .p-dropdown-trigger').click()
    cy.get('[ng-reflect-label="c1"] > .p-ripple').click()
    cy.get('#year').type('2001-01-01')
    cy.get('#addBook > .p-ripple').click().wait(5000)
    cy.location('pathname').should('eq', '/books')
    cy.contains('Book Name')
  })

  it('Should not create new book', () => {
    cy.get('#name').type('nv')
    cy.get('#author').type('nv')
    cy.get('input[type=file]').first().selectFile('cypress/fixtures/files/test.pdf', {force: true})
    cy.get('input[type=file]').last().selectFile('cypress/fixtures/images/img.png', {force: true})
    cy.get('#tags > .p-dropdown > .p-dropdown-trigger').click().wait(5000)
    cy.get('[ng-reflect-label="tag1"] > .p-ripple').click()
    cy.get('#categories > .p-dropdown > .p-dropdown-trigger').click().wait(5000)
    cy.get('[ng-reflect-label="c1"] > .p-ripple').click()
    cy.get('#year').type('2001-01-01')
    cy.get('#addBook > .p-ripple').click().wait(5000)
    cy.location('pathname').should('eq', '/add-book')
  })

})