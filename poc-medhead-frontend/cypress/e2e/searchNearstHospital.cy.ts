describe('Search nearest hospital by speciality', () => {
  beforeEach(() => {
    // Se connecter en tant que patient
    cy.visit('/login');
    cy.fixture('userPatientLogin').then((loginData) => {
      cy.get('[name=email]').type(loginData.email);
      cy.get('[name=password]').type(loginData.password);
      cy.contains('button', 'Connexion').click();
      cy.location('pathname').should('equal', '/hospital');
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
    cy.get('ng-select[name="speciality"]').click().type('Cardiologie{enter}');

    // Vérifier que le bouton "Valider" est désactivé
    cy.contains('button', 'Valider').should('be.disabled');
  });

  it('should enable the submit button and perform the search', () => {
    // Naviguer vers la page de recherche d'hôpitaux
    cy.contains('button', 'Rechercher un hôpital').click();
    cy.location('pathname').should('equal', '/searchHospital');

    // Remplir le champ d'adresse
    cy.get('input[name="address"]').type('75004 Paris');

    // Sélectionner une spécialité dans le champ de sélection
    cy.get('ng-select[name="speciality"]').click().type('Cardiologie{enter}');

    // Vérifier que le bouton "Valider" est activé
    cy.contains('button', 'Valider').should('not.be.disabled');

    // Cliquer sur le bouton "Valider" pour effectuer la recherche
    cy.contains('button', 'Valider').click();

    // Vérifier que les résultats de recherche sont affichés
    cy.get('.hospital-card').should('be.visible');
    cy.get('.hospital-card').should('have.length.greaterThan', 0);

    // Vérifier que chaque résultat contient les informations nécessaires
    cy.get('.hospital-card').each(($card) => {
      cy.wrap($card).find('h5').should('contain.text', 'Hôpital');
      cy.wrap($card).find('.fa-location-dot').should('be.visible');
      cy.wrap($card).find('.fa-bed').should('be.visible');
      cy.wrap($card).find('.fa-road').should('be.visible');
    });
  });
});
