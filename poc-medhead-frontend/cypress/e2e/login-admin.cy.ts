describe('Login-Admin', () => {
  it('Should login and verify user data in localStorage', () => {
    cy.visit('/');
    cy.url().should('include', 'login');

    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);

      // Intercept the authentication request
      cy.intercept('POST', 'http://localhost:8222/api/auth/authenticate').as(
        'loginRequest'
      );

      // Click on the login button
      cy.contains('button', 'Connexion').click();

      // Wait for the login request to complete
      cy.wait('@loginRequest').then((interception) => {
        // Check if the response exists before accessing its properties
        if (interception.response) {
          expect(interception.response.statusCode).to.equal(200);
        } else {
          throw new Error('No response received for the login request.');
        }
      });

      // Verify that the user is redirected to the hospital page
      cy.location('pathname', { timeout: 20000 }).should('equal', '/hospital');

      // Verify the content on the page
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

      // Intercept the authentication request
      cy.intercept('POST', 'http://localhost:8222/api/auth/authenticate').as(
        'loginRequest'
      );

      // Click on the login button
      cy.contains('button', 'Connexion').click();

      // Wait for the login request to complete
      cy.wait('@loginRequest').then((interception) => {
        // Check if the response exists before accessing its properties
        if (interception.response) {
          expect(interception.response.statusCode).to.equal(200);
        } else {
          throw new Error('No response received for the login request.');
        }
      });

      // Verify that the user is redirected to the hospital page
      cy.location('pathname', { timeout: 20000 }).should('equal', '/hospital');

      // Verify the content on the page
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

            // Verify navigation links for admin
            cy.get('nav.navbar').within(() => {
              cy.contains('a.nav-link', 'Reservations').should('be.visible');
              cy.contains('a.nav-link', 'Hôpitaux').should('be.visible');
              cy.contains('a.nav-link', 'Home').should('be.visible');
            });

            // Verify the "gérer les hôpitaux" button
            cy.get('button.btn-manage-hospitals').should('be.visible');
          });
        });
    });
  });
});
