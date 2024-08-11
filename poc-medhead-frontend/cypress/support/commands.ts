Cypress.Commands.add('mockLoginPatient', () => {
  cy.fixture('mockPatientData').then((mockData) => {
    // Interception des requêtes de login et mock des réponses avec les données du fichier JSON
    cy.intercept('POST', '/api/auth/authenticate', {
      statusCode: 200,
      body: {
        token: mockData.token,
        user: mockData.user,
      },
    }).as('loginRequest');

    // Visiter la page de login et remplir les informations
    cy.visit('/login');
    cy.fixture('userPatientLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

      // Attendre que les requêtes soient traitées
      cy.wait('@loginRequest');

      // Vérifier que l'utilisateur est redirigé vers la page "hospital"
      cy.location('pathname').should('equal', '/hospital');
      cy.contains('MedHead Urgences', { timeout: 10000 }).should('be.visible');

    });
  });
});
Cypress.Commands.add('mockLoginAdmin', () => {
  cy.fixture('mockAdminData').then((mockData) => {
    // Interception des requêtes de login et mock des réponses avec les données du fichier JSON
    cy.intercept('POST', '/api/auth/authenticate', {
      statusCode: 200,
      body: {
        token: mockData.token,
        user: mockData.user,
      },
    }).as('loginRequest');

    // Visiter la page de login et remplir les informations
    cy.visit('/login');
    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

      // Attendre que les requêtes soient traitées
      cy.wait('@loginRequest');

      // Vérifier que l'utilisateur est redirigé vers la page "hospital"
      cy.location('pathname').should('equal', '/hospital');
      cy.get('.btn-manage-hospitals').should('be.visible');
    });
  });
});
