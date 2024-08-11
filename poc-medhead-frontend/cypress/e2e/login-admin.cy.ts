describe('Login-Admin', () => {
  beforeEach(() => {
    cy.fixture('mockAdminData').then((mockData) => {
      // Interception des requêtes de login et mock des réponses avec les données du fichier JSON
      cy.intercept('POST', '/api/auth/authenticate', {
        statusCode: 200,
        body: {
          token: mockData.token,
          user: mockData.user,
        },
      }).as('loginRequest');

      // Interception de la requête pour récupérer les données utilisateur
      cy.intercept('GET', '/api/auth', {
        statusCode: 200,
        body: mockData.user,
      }).as('getUserRequest');
    });
  });

  it('Should login and verify user data in localStorage', () => {
    cy.visit('/');
    cy.url().should('include', 'login');

    cy.fixture('userAdminLogin').then((loginData) => {
      // Enter valid email and password
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

      // Attendre que la requête de login soit terminée
      cy.wait('@loginRequest');

      // Vérifiez que l'utilisateur est redirigé vers la page d'accueil après la connexion
      cy.location('pathname').should('equal', '/hospital');

      cy.contains('MedHead Urgences')
        .should('be.visible')
        .then(() => {
          // Vérifiez les données dans `localStorage`
          const userString = window.localStorage.getItem('authenticated-user');
          const token = window.localStorage.getItem('token');

          expect(userString).to.be.a('string');

          if (userString) {
            const user = JSON.parse(userString);

            expect(user).to.be.an('object');
            expect(user).to.have.keys([
              'id',
              'nom',
              'prenom',
              'dateNaissance',
              'email',
              'sexe',
              'adresse',
              'numero',
              'roles',
              'accountLocked',
              'enabled',
            ]);
          }

          expect(token).to.be.a('string');
        });

      cy.get('button').find('i.fa-door-open').click();
      cy.location('pathname').should('equal', '/login');
    });
  });

  it('Should login and verify user role is Admin', () => {
    cy.visit('/');
    cy.url().should('include', 'login');

    cy.fixture('userAdminLogin').then((loginData) => {
      // Enter valid email and password
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

      // Attendre que la requête de login soit terminée
      cy.wait('@loginRequest');

      // Vérifiez que l'utilisateur est redirigé vers la page d'accueil après la connexion
      cy.location('pathname').should('equal', '/hospital');

      cy.contains('MedHead Urgences')
        .should('be.visible')
        .then(() => {
          // Vérifiez les données dans `localStorage`
          const userString = window.localStorage.getItem('authenticated-user');
          const token = window.localStorage.getItem('token');

          expect(userString).to.be.a('string');

          if (userString) {
            const user = JSON.parse(userString);

            expect(user).to.be.an('object');
            expect(user.roles).to.include('Admin');
          }

          expect(token).to.be.a('string');

          cy.get('nav.navbar').within(() => {
            cy.contains('a.nav-link', 'Reservations').should('be.visible');
            cy.contains('a.nav-link', 'Hôpitaux').should('be.visible');
            cy.contains('a.nav-link', 'Home').should('be.visible');
          });
          cy.contains('button', 'gérer les hôpiteaux').should('be.visible');
        });
    });
  });
});
