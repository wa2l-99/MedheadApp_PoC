describe('Login-Admin', () => {
  it('Should login and verify user data in localStorage', () => {
    cy.visit('/');
    cy.url().should('include', 'login');

    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);

      cy.intercept('POST', '**/api/auth/authenticate').as('loginRequest');

      cy.contains('button', 'Connexion').click();

      cy.wait('@loginRequest', { timeout: 20000 }).then((interception) => {
        if (interception.response) {
          expect(interception.response.statusCode).to.equal(200);
        } else {
          throw new Error('No response received for the login request.');
        }
      });

      cy.location('pathname').should('equal', '/hospital');

      cy.contains('MedHead Urgences')
        .should('be.visible')
        .then(() => {
          cy.window().then((win) => {
            const userString = win.localStorage.getItem('authenticated-user');
            const token = win.localStorage.getItem('token');
            expect(userString).to.be.a('string');
            expect(token).to.be.a('string');
          });
        });
    });
  });

  it('Should login and verify user role is Admin', () => {
    cy.visit('/');
    cy.url().should('include', 'login');

    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);

      cy.intercept('POST', '**/api/auth/authenticate').as('loginRequest');

      cy.contains('button', 'Connexion').click();

      cy.wait('@loginRequest', { timeout: 20000 }).then((interception) => {
        if (interception.response) {
          expect(interception.response.statusCode).to.equal(200);
        } else {
          throw new Error('No response received for the login request.');
        }
      });

      cy.location('pathname').should('equal', '/hospital');

      cy.contains('MedHead Urgences')
        .should('be.visible')
        .then(() => {
          cy.window().then((win) => {
            const userString = win.localStorage.getItem('authenticated-user');
            const token = win.localStorage.getItem('token');
            expect(userString).to.be.a('string');

            if (userString) {
              const user = JSON.parse(userString);
              expect(user.roles).to.include('Admin');
            }

            expect(token).to.be.a('string');

            cy.get('nav.navbar').within(() => {
              cy.contains('a.nav-link', 'Reservations').should('be.visible');
              cy.contains('a.nav-link', 'Hôpitaux').should('be.visible');
              cy.contains('a.nav-link', 'Home').should('be.visible');
            });
            cy.get('button.btn-manage-hospitals').should('be.visible');
          });
        });
    });
  });
});
