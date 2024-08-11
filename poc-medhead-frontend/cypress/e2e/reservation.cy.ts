describe('Hospital Reservation - Scenarios', () => {
  beforeEach(() => {
    cy.mockLoginPatient();

    // Interception de la requête API pour les spécialités
    cy.fixture('mockSpecialities').then((mockData) => {
      cy.intercept('GET', '**/api/hospital/specialities', {
        statusCode: 200,
        body: mockData,
      }).as('getSpecialities');
    });
    // Interception de la requête de recherche d'hôpitaux
    cy.fixture('mockHospitalSearchResults').then((mockData) => {
      cy.intercept('GET', '**/api/hospital/nearest*', {
        statusCode: 200,
        body: mockData,
      }).as('searchHospitals');
    });

    // Interception de la requête de réservation
    cy.fixture('mockReservationData').then((mockReservation) => {
      cy.intercept('POST', '**/api/reservations/addReservation', {
        statusCode: 200,
        body: mockReservation,
      }).as('addReservation');
    });

    cy.contains('button', 'Rechercher un hôpital').click();
    cy.location('pathname').should('equal', '/searchHospital');

    // Effectuer une recherche valide
    cy.get('input[name="address"]').type('75004 Paris');

    // Attendre que les spécialités soient chargées
    cy.wait('@getSpecialities').then((interception) => {
      cy.log(
        'Données de spécialités interceptées:',
        interception.response?.body
      );
      expect(interception.response?.statusCode).to.eq(200);
    });

    // Ouvrir le dropdown des spécialités
    cy.get('ng-select[name="speciality"]').click({ force: true });

    // Forcer la recherche de la spécialité en saisissant du texte
    cy.get('ng-select[name="speciality"] input[type="text"]', {
      timeout: 10000,
    })
      .should('be.visible')
      .type('Cardiologie', { force: true });

    // Vérifiez que le dropdown contient l'option "Cardiologie"
    cy.get('.ng-dropdown-panel-items')
      .should('be.visible')
      .then(($dropdown) => {
        const dropdownText = $dropdown.text();
        cy.log('Contenu du dropdown:', dropdownText);
        expect(dropdownText).to.include('Cardiologie');
      });

    // Sélectionner "Cardiologie" dans le dropdown
    cy.get('.ng-dropdown-panel-items').contains('Cardiologie').click();

    cy.contains('button', 'Valider').click();

    // Attendre que les résultats de recherche soient interceptés et traités
    cy.wait('@searchHospitals');

    // Vérifier que les résultats de recherche sont affichés
    cy.get('.hospital-card').should('be.visible');
  });

  it('should display a success toast and open the reservation modal with correct details when "Réserver immédiatement" is clicked', () => {
    // Cliquer sur le bouton "Réserver immédiatement" du premier résultat
    cy.get('.hospital-card').first().contains('Réserver immédiatement').click();

    cy.wait('@addReservation').then((interception) => {
      if (interception.response) {
        const response = interception.response;
        cy.get('.modal-content').should('be.visible');

        // Vérifier les détails dans le modal
        cy.get('.modal-title').should('contain', 'Détails de la réservation');
        cy.get('.modal-body').within(() => {
          cy.contains('Référence de la réservation:').should('be.visible');
          cy.get('.result-res').should('contain', response.body.reference);
          cy.contains("Nom de l'organisation:").should('be.visible');
          cy.get('.result-res').should(
            'contain',
            response.body.hospital.nomOrganisation
          );

          cy.contains("Adresse de l'organisation:").should('be.visible');
          cy.get('.result-res').should(($div) => {
            const text = $div
              .text()
              .replace(/\u00A0/g, ' ')
              .trim(); //
            const adresseComplete = `${response.body.hospital.adresse} ${response.body.hospital.codePostal}`;

            expect(text).to.contain(adresseComplete);
          });

          cy.contains('Spécialitéd médicales:').should('be.visible');
          cy.get('.result-res').should(
            'contain',
            response.body.hospital.specialitesMedicales[0].nom
          );

          cy.contains('Nom et Prénom patient:').should('be.visible');

          cy.get('.result-res').should(($div) => {
            const text = $div
              .text()
              .replace(/\u00A0/g, ' ')
              .trim();
            const nomPrenom = `${response.body.patient.nom} ${response.body.patient.prenom}`;
            expect(text).to.contain(nomPrenom);
          });

          cy.contains('Numéro téléphone patient:').should('be.visible');
          cy.get('.result-res').should('contain', response.body.patient.numero);

          cy.contains('Email patient:').should('be.visible');
          cy.get('.result-res').should('contain', response.body.patient.email);
        });
        cy.get('.toast-success')
          .should('be.visible')
          .and('contain', 'Réservation effectuée avec succès');
      } else {
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
