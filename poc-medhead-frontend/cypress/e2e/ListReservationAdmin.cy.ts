describe('Admin view: Access Reservations Page', () => {
  beforeEach(() => {
    // Se connecter en tant qu'admin
    cy.visit('/login');
    cy.fixture('userAdminLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();
      cy.location('pathname').should('equal', '/hospital');
    });
  });

  it('Should navigate to the Reservations page through the navbar', () => {

    cy.get('.nav-link').contains('Reservations').click();

    // Vérifier que l'URL change et que la page des réservations est affichée
    cy.location('pathname').should('equal', '/reservations');
    cy.contains('Liste des réservations').should('be.visible');
    cy.get('table.table-striped').should('be.visible');

    // Vérifier que la table des réservations est remplie
    cy.get('table.table-striped tbody tr').should('have.length.greaterThan', 0);

    cy.get('.pagination').should('be.visible');
  });

  it('Should open the specialties dropdown and check for specialties', () => {
    // Accéder à la page des réservations via la barre de navigation
    cy.get('.nav-link').contains('Reservations').click();

    // Vérifier que la table des réservations est visible
    cy.get('table.table-striped tbody tr').should('have.length.greaterThan', 0);

    // Ouvrir le dropdown des spécialités pour la première réservation
    cy.get('table.table-striped tbody tr').first().within(() => {
      cy.get('button').contains('Spécialités').click();
    });

    // Vérifier que le dropdown affiche les spécialités
    cy.get('.dropdown-menu').within(() => {
      cy.get('span').should('have.length.greaterThan', 0);
    });
  });
});
