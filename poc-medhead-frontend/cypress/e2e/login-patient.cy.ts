describe('Login-Patient', () => {
  it('Should login and verify user data in localStorage', () => {
    cy.visit('/');
    cy.url().should('include', 'login'); // Utilisez `include` au lieu de `includes`

    cy.fixture('userPatientLogin').then((loginData) => {
      // Enter valid email and password
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

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

  it('Should login and verify user role is Patient', () => {
    cy.visit('/');
    cy.url().should('include', 'login'); // Utilisez `include` au lieu de `includes`

    cy.fixture('userPatientLogin').then((loginData) => {
      // Enter valid email and password
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();

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

            expect(user.roles).to.include('Patient');
          }

          expect(token).to.be.a('string');

          cy.get('nav.navbar').within(() => {
            cy.contains('a.nav-link', 'Hôpital').should('be.visible');
            cy.contains('a.nav-link', 'Home').should('be.visible');
          });
          cy.contains('button', 'Rechercher un hôpital').should('be.visible');
        });
    });
  });
});
