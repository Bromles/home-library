import {User} from "../support/user";

describe('Show Books', () => {
  let r = (Math.random() + 1).toString(36).substring(7);
  let firstUser = new User('first[' + r + ']', 'password');
  let secondUser = new User('second[' + r + ']', 'password');

  before('Create users books', () => {
    cy.visit('/')
    cy.createUser(firstUser)
    cy.get('#addBook').click().wait(1000)
    cy.addUserBook(firstUser)
    cy.logout()

    cy.visit('/')
    cy.createUser(secondUser)
    cy.get('#addBook').click().wait(1000)
    cy.addUserBook(secondUser)
    cy.logout()
  })

  beforeEach('Login', () => {
    cy.visit('/')
    cy.login(firstUser).wait(3000)

  })

  it('Read', () => {
    cy.get('.product-grid-item-content').contains('Book of ' + firstUser.username).click().wait(4000);
    cy.get('#open').click().wait(6000)
    cy.get('#PDFViewer').should('contain', 'A Simple PDF File')
    cy.get('#exitPDF').click().wait(2000)
    cy.url().should('contain', 'books/')
  })

  it('Download', () => {
    cy.readFile("cypress/downloads/test.pdf").should('not.exist')
    cy.get('.product-grid-item-content').contains('Book of ' + firstUser.username).click().wait(4000);

    const loadScript = '<script> setTimeout(() => location.reload(), 3000); </script>';
    cy.get('#download').invoke('append', loadScript);
    cy.get('#download').click().wait(10000)
    cy.readFile("cypress/downloads/test.pdf")
  })


  it('Modify: success', () => {
    cy.get('.product-grid-item-content').contains('Book of ' + firstUser.username).click().wait(4000);
    modifyBookFields()
    cy.get('#save > .p-ripple').click().wait(4000)
    cy.get('.appLogo > span').click().wait(1000)
    cy.get('.product-grid-item-content').should('contain','Book of ' + firstUser.username + ' #Modified')
  })

  it('Modify: forbidden', () => {
    cy.get('.product-grid-item-content').contains('Book of ' + secondUser.username).click().wait(4000);
    modifyBookFields()
    cy.wait(3000)
    cy.get('#save > .p-ripple').should('not.exist')
    //cy.get('#save > .p-ripple').click().wait(4000)
    cy.get('.appLogo > span').click().wait(1000)
    cy.get('.product-grid-item-content').should('contain','Book of ' + secondUser.username)
  })

  it('Delete: success', () => {
    cy.get('.product-grid-item-content').contains('Book of ' + firstUser.username + ' #Modified').click().wait(4000);
    cy.get('#delete > .p-ripple').click().wait(4000)
    cy.get('.appLogo > span').click().wait(1000)
    cy.get('.product-grid-item-content')
      .contains('Book of ' + firstUser.username + ' #Modified')
      .should('not.exist')
  })

  it('Delete: forbidden', () => {
    cy.get('.product-grid-item-content').contains('Book of ' + secondUser.username).click().wait(4000);
    cy.get('#delete > .p-ripple').should('not.exist')
  })


})

function modifyBookFields(){
  cy.get('#float-input').type(' #Modified')
  cy.get('#author').type(' #Modified')
  cy.get('#tags > .p-dropdown > .p-dropdown-trigger').click()
  cy.get('#pr_id_1_list').type(' #Modified')
  cy.get('#categories > .p-dropdown > .p-dropdown-trigger').click()
  cy.get('#pr_id_2_list').contains("c1").click()
  cy.get('#year').clear().type('2012-01-01')
}
