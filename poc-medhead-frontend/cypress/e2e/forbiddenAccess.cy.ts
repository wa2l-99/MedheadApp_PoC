describe('Protected Resource Access', () => {
  beforeEach(() => {
    cy.mockLoginAdmin();
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
