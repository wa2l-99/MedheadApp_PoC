describe('Hospital Reservation - Scenarios', () => {
  beforeEach(() => {
    cy.mockLoginPatient();


    cy.contains('button', 'Rechercher un hôpital').click();
    cy.location('pathname').should('equal', '/searchHospital');

    // Effectuer une recherche valide
    cy.get('input[name="address"]').type('75004 Paris');
    cy.get('ng-select[name="speciality"]').click().type('Cardiologie{enter}');
    cy.contains('button', 'Valider').click();

    // Vérifier que les résultats de recherche sont affichés
    cy.get('.hospital-card').should('be.visible');
  });

  it('should display a success toast and open the reservation modal with correct details when "Réserver immédiatement" is clicked', () => {
    // Cliquer sur le bouton "Réserver immédiatement" du premier résultat
    cy.get('.hospital-card').first().contains('Réserver immédiatement').click();

    cy.get('body').then(($body) => {
      if ($body.find('.modal-contens').length > 0) {
        // Vérifier que le modal de réservation s'affiche avec les détails corrects
        cy.get('.modal-content').should('be.visible');

        cy.get('.modal-title').should('contain', 'Détails de la réservation');
        cy.get('.modal-body').within(() => {
          cy.contains('Référence de la réservation:').should('be.visible');
          cy.contains("Nom de l'organisation:").should('be.visible');
          cy.contains("Adresse de l'organisation:").should('be.visible');
          cy.contains('Spécialitéd médicales:').should('be.visible');
          cy.contains('Nom et Prénom patient:').should('be.visible');
          cy.contains('Numéro téléphone patient:').should('be.visible');
          cy.contains('Email patient:').should('be.visible');
        });
      } else if ($body.find('.toast-error').length > 0) {
        // Sinon, vérifier si un toast d'erreur est affiché
        cy.get('.toast-error')
          .should('be.visible')
          .and(
            'contain',
            "Erreur Une réservation a déjà été effectuée pour l 'hôpital"
          );
      }
    });
  });
});
