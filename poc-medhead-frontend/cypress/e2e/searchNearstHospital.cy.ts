describe('Search nearest hospital by speciality', () => {
  beforeEach(() => {
    cy.mockLoginPatient();

    // Intercepter la requête API pour utiliser les données mockées
    cy.fixture('mockHospitalSearchResults').then((mockData) => {
      cy.intercept('GET', '**/api/hospital/nearest*', {
        statusCode: 200,
        body: mockData,
      }).as('getNearestHospitals');
    });
  });

  it('Visits the search hospital page', () => {
    // Naviguer vers la page de recherche d'hôpitaux
    cy.contains('button', 'Rechercher un hôpital').click();
    cy.location('pathname').should('equal', '/searchHospital');
    cy.contains('Recherche du Proche Hôpital par Spécialité').should(
      'be.visible'
    );
  });

  it('should disable the submit button if the address is empty', () => {
    // Naviguer vers la page de recherche d'hôpitaux
    cy.contains('button', 'Rechercher un hôpital').click();
    cy.location('pathname').should('equal', '/searchHospital');

    // Laisser le champ d'adresse vide
    cy.get('input[name="address"]').clear();

    // Sélectionner une spécialité dans le champ de sélection
    cy.get('ng-select[name="speciality"]')
      .should('be.visible')
      .wait(500)
      .click()
      .type('Cardiologie{enter}');

    // Vérifier que le bouton "Valider" est désactivé
    cy.contains('button', 'Valider').should('be.disabled');
  });

  it('should enable the submit button and perform the search', () => {
    // Naviguer vers la page de recherche d'hôpitaux
    cy.contains('button', 'Rechercher un hôpital').click();
    cy.location('pathname').should('equal', '/searchHospital');

    // Remplir le champ d'adresse
    cy.get('input[name="address"]').type('75004 Paris');

    cy.get('ng-select[name="speciality"]')
      .should('be.visible')
      .click()
      .type('Cardiologie');

    cy.get('.ng-dropdown-panel-items').should('be.visible');
    cy.get('.ng-dropdown-panel-items').contains('Cardiologie').click();

    // Vérifier que le bouton "Valider" est activé
    cy.contains('button', 'Valider').should('not.be.disabled');

    cy.contains('button', 'Valider').click();

    // Attendre que la requête soit interceptée et traitée
    cy.wait('@getNearestHospitals');

    // Vérifier que les résultats de recherche sont affichés à partir des données mockées
    cy.get('.hospital-card').should('be.visible');
    cy.get('.hospital-card').should('have.length', 2);

    // Vérifier que chaque résultat contient les informations nécessaires
    cy.get('.hospital-card').each(($card, index) => {
      const hospitals = [
        {
          nomOrganisation: 'Hôpital Pitié-Salpêtrière',
          adresse: "47-83 Boulevard de l'Hôpital",
          codePostal: '75013',
          specialitesMedicales: ['Cardiologie', 'Neurologie'],
          litsDisponible: 977,
          distance: '0.421 km',
        },
        {
          nomOrganisation: 'Hôpital Necker',
          adresse: '149 Rue de Sèvres',
          codePostal: '75015',
          specialitesMedicales: ['Ophtalmologie', 'Cardiologie'],
          litsDisponible: 483,
          distance: '3.962 km',
        },
      ];

      cy.wrap($card)
        .find('h5')
        .should('contain.text', hospitals[index].nomOrganisation);
      cy.wrap($card).find('.fa-location-dot').should('be.visible');
      cy.wrap($card).find('.fa-bed').should('be.visible');
      cy.wrap($card).find('.fa-road').should('be.visible');
    });
  });
});
