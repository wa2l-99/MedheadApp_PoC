describe('Admin view: List of All hospitals', () => {
  beforeEach(() => {
    // Se connecter en tant qu'admin
    cy.mockLoginAdmin();
  });

  it('Visits the All hospitals page and checks hospital list', () => {
    // Naviguer vers la page des hôpitaux
    cy.get('.btn-manage-hospitals').click();
    cy.location('pathname').should('equal', '/hospitals');
    cy.contains('Liste des hôpitaux').should('be.visible');

    // Vérifier que la liste des hôpitaux est visible
    cy.get('.hospital-card').should('have.length.greaterThan', 0);

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

    // Vérifier la pagination
    cy.get('.pagination').should('be.visible');
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
  });
});
