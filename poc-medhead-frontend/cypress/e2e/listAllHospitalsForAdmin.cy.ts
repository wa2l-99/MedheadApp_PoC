describe('Admin view: List of All hospitals', () => {
  beforeEach(() => {
    cy.mockLoginAdmin();

    cy.fixture('mockHospitals').then((mockData) => {
      cy.intercept('GET', '**/api/hospital*', {
        statusCode: 200,
        body: mockData,
      }).as('getHospitals');
    });

    cy.visit('/hospitals');
    cy.wait('@getHospitals');
  });

  it('Visits the All hospitals page and checks hospital list', () => {
    cy.location('pathname').should('equal', '/hospitals');
    cy.contains('Liste des hôpitaux').should('be.visible');

    cy.get('.hospital-card').should('have.length', 3);

    // Vérifier les éléments dans chaque carte d'hôpital
    cy.get('.hospital-card').each(($card) => {
      cy.wrap($card).find('h5').should('contain.text', 'Hôpital');
      cy.wrap($card).find('.fa-pen-to-square').should('be.visible');
      cy.wrap($card).find('.fa-trash-can').should('be.visible');
      cy.wrap($card).find('.fa-location-dot').should('be.visible');
      cy.wrap($card).find('.fa-envelope').should('be.visible');
      cy.wrap($card).find('.fa-hand-holding-medical').should('be.visible');
      cy.wrap($card).find('.fa-bed').should('be.visible');
      cy.wrap($card).find('.fa-pencil').should('be.visible');
    });

    cy.get('body').then(($body) => {
      if ($body.find('.pagination .page-item').length > 1) {
        // Tester la pagination s'il y a plus d'une page
        cy.get('.pagination .page-item').should('have.length.greaterThan', 1);

        // Stocker le texte de la première carte d'hôpital pour comparaison
        cy.get('.hospital-card')
          .first()
          .invoke('text')
          .then((firstCardText) => {
            cy.get('.pagination').contains('2').click();

            // Vérifier que le contenu a changé après avoir cliqué sur la page 2
            cy.get('.hospital-card')
              .first()
              .invoke('text')
              .should((secondCardText) => {
                expect(secondCardText).not.to.eq(firstCardText);
              });
          });
      } else {
        cy.log("Pagination n'existe pas, le test de pagination est sauté.");
      }
    });
  });
});
