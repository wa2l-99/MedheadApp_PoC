describe('Login-Fail', () => {
  it('Does not log in with invalid password', () => {
    cy.visit('/');
    cy.location('pathname').should('equal', '/login');
    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.wrongPassword);
      cy.contains('button', 'Connexion').click();

      // still on /login page plus an error is displayed
      cy.location('pathname').should('equal', '/login');
      // Vérifiez que le toast d'erreur est affiché
      cy.get('.toast-error')
        .should('be.visible')
        .and('contain', 'Identifiant et/ou mot de passe incorrect');
    });
  });
  it('Does not log in with invalid email', () => {
    cy.visit('/');
    cy.location('pathname').should('equal', '/login');

    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.wrongemail);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

      // still on /login page plus an error is displayed
      cy.location('pathname').should('equal', '/login');
      // Vérifiez que le toast d'erreur est affiché
      cy.get('.toast-error')
        .should('be.visible')
        .and('contain', 'Identifiant et/ou mot de passe incorrect');
    });
  });
  it('Does not log in with empty email', () => {
    cy.visit('/');
    cy.location('pathname').should('equal', '/login');
    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').clear();
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

      // still on /login page plus an error is displayed
      cy.location('pathname').should('equal', '/login');
      // Vérifiez que le toast d'erreur est affiché
      cy.get('.toast-error')
        .should('be.visible')
        .and('contain', "L'email est obligatoire");
    });
  });
  it('Does not log in with empty password', () => {
    cy.visit('/');
    cy.location('pathname').should('equal', '/login');
    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').clear();
      cy.contains('button', 'Connexion').click();

      // still on /login page plus an error is displayed
      cy.location('pathname').should('equal', '/login');
      // Vérifiez que le toast d'erreur est affiché

      cy.get('.toast-error')
        .should('be.visible')
        .and('contain', 'Le mot de passe est obligatoire');
      cy.get('.toast-error')
        .should('be.visible')
        .and('contain', 'Le mot de passe doit contenir au moins 8 caractères');
    });
  });

  it('Does not log in with empty password', () => {
    cy.visit('/');
    cy.location('pathname').should('equal', '/login');

    cy.get('[name=email]').clear();
    cy.get('[name=password]').clear();
    cy.contains('button', 'Connexion').click();

    // still on /login page plus an error is displayed
    cy.location('pathname').should('equal', '/login');
    // Vérifiez que le toast d'erreur est affiché
    cy.get('.toast-error')
      .should('be.visible')
      .and('contain', "L'email est obligatoire");
    cy.get('.toast-error')
      .should('be.visible')
      .and('contain', 'Le mot de passe est obligatoire');
    cy.get('.toast-error')
      .should('be.visible')
      .and('contain', 'Le mot de passe doit contenir au moins 8 caractères');
  });
});
