it('Does not log in with invalid input (validation errors)', () => {
  cy.intercept('POST', '**/api/auth/authenticate', {
    statusCode: 400,
    body: {
      validationErrors: [
        "L'email est obligatoire",
        'Le mot de passe doit contenir au moins 8 caractères',
      ],
    },
  }).as('loginRequest');

  cy.visit('/');
  cy.location('pathname').should('equal', '/login');

  cy.get('[name=email]').clear();
  cy.get('[name=password]').clear();
  cy.contains('button', 'Connexion').click();

  // Attendre que la requête soit traitée
  cy.wait('@loginRequest');

  // Vérifiez que chaque message d'erreur est affiché
  cy.get('.toast-error')
    .should('be.visible')
    .and('contain', "L'email est obligatoire");
  cy.get('.toast-error')
    .should('be.visible')
    .and('contain', 'Le mot de passe doit contenir au moins 8 caractères');
});
it('Does not log in with missing email', () => {
  cy.intercept('POST', '**/api/auth/authenticate', {
    statusCode: 400,
    body: {
      error: "L'email est obligatoire",
    },
  }).as('loginRequest');

  cy.visit('/');
  cy.location('pathname').should('equal', '/login');

  cy.get('[name=email]').clear();
  cy.get('[name=password]').type('validPassword');
  cy.contains('button', 'Connexion').click();

  // Attendre que la requête soit traitée
  cy.wait('@loginRequest');

  // Vérifiez que le message d'erreur est affiché
  cy.get('.toast-error')
    .should('be.visible')
    .and('contain', "L'email est obligatoire");
});
it('Does not log in with incorrect password', () => {
  cy.intercept('POST', '**/api/auth/authenticate', {
    statusCode: 401,
    body: {
      error: 'Identifiant et/ou mot de passe incorrect',
    },
  }).as('loginRequest');

  cy.visit('/');
  cy.location('pathname').should('equal', '/login');

  cy.fixture('userAdminLogin').then((loginData) => {
    cy.get('[name=email]').type(loginData.email);
    cy.get('[name=password]').type('wrongPassword');
    cy.contains('button', 'Connexion').click();

    // Attendre que la requête soit traitée
    cy.wait('@loginRequest');

    // Vérifiez que le message d'erreur est affiché
    cy.get('.toast-error')
      .should('be.visible')
      .and('contain', 'Identifiant et/ou mot de passe incorrect');
  });
});
it('Handles unexpected server error', () => {
  cy.intercept('POST', '**/api/auth/authenticate', {
    statusCode: 500,
    body: {},
  }).as('loginRequest');

  cy.visit('/');
  cy.location('pathname').should('equal', '/login');

  cy.get('[name=email]').type('validEmail@gmail.com');
  cy.get('[name=password]').type('validPassword');
  cy.contains('button', 'Connexion').click();

  // Attendre que la requête soit traitée
  cy.wait('@loginRequest');

  // Vérifiez que le message d'erreur générique est affiché
  cy.get('.toast-error')
    .should('be.visible')
    .and('contain', "Une erreur inattendue s'est produite");
});
