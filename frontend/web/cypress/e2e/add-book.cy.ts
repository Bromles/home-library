import {User} from "../support/user";

describe('Add book', () => {

  let user = new User('test', 'test');

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
    cy.get('#pr_id_1_list').first().click()
    cy.get('#categories > .p-dropdown > .p-dropdown-trigger').click()
    cy.get('#pr_id_2_list').first().click()
    cy.get('#year').type('2001-01-01')
    cy.get('#addBook > .p-ripple').click()
    cy.location('pathname').should('eq', '/books')
    cy.contains('Book Name')
  })

  it('Should not create new book', () => {
    cy.get('#name').type('nv')
    cy.get('#author').type('nv')
    cy.get('input[type=file]').first().selectFile('cypress/fixtures/files/test.pdf', {force: true})
    cy.get('input[type=file]').last().selectFile('cypress/fixtures/images/img.png', {force: true})
    cy.get('#tags > .p-dropdown > .p-dropdown-trigger').click()
    cy.get('#pr_id_1_list').first().click()
    cy.get('#categories > .p-dropdown > .p-dropdown-trigger').click()
    cy.get('#pr_id_2_list').first().click()
    cy.get('#year').type('2001-01-01')
    cy.get('#addBook > .p-ripple').click()
    cy.location('pathname').should('eq', '/add-book')
  })

})