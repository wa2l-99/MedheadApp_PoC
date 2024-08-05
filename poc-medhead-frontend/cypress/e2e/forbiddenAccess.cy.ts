describe('Protected Resource Access', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.fixture('userPatientLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();
      cy.location('pathname').should('equal', '/hospital');
    });
  });

  it('fails to access protected resource and redirects to forbidden page', () => {
    // Try to visit the protected resource
    cy.visit('/hospitals', { failOnStatusCode: false });

    // Verify redirection to the forbidden page
    cy.location('pathname').should('equal', '/forbidden');

    // Verify the presence of the forbidden image
    cy.get('img[alt=""]')
      .should('have.attr', 'src', '/forbidden.png')
      .and('be.visible');
  });
});
