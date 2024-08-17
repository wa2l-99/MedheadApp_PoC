# PocMedheadFrontend

Ce projet est le frontend de la plateforme MedHead, développé avec [Angular CLI](https://github.com/angular/angular-cli) version 18.1.0.


## Prérequis

- **Node.js** version 20.x ou plus récent
- **Angular CLI** installé globalement (`npm install -g @angular/cli`)
- **Backend MedHead** : Assurez-vous que le backend est configuré et lancé selon les instructions fournies dans le dossier `services/`.

## Installation

  1. **Cloner le repository :**
  
     ```bash
     git clone git@github.com:wa2l-99/MedheadApp_PoC.git
     cd poc-medhead-frontend
  
  2. **Installer les dépendances :**
    
     ```bash
     npm install
  
  **Assurez-vous que tous les microservices backend sont en cours d'exécution**


## Lancer le Projet

Pour lancer l'application en mode développement :
  
  `npm start`

L'application sera accessible à l'adresse suivante :

  `http://localhost:4200`


## Connexion et Utilisation

### Comptes Prédéfinis

- **Administrateur :**
  - **Email :** `admin@example.com`
  - **Mot de passe :** `admin_password`

- **Patient (compte de test) :**
  - **Email :** `patient@example.com`
  - **Mot de passe :** `patient_password`
    

### Création de Nouveau Compte Utilisateur

Si vous créez un nouveau compte utilisateur, vous devrez activer ce compte en utilisant un code d'activation envoyé par email. 
Comme nous sommes en environnement de développement, nous utilisons MailDev pour simuler l'envoi des email.

1. Inscription : Remplissez le formulaire d'inscription avec les informations demandées.
2. Récupération du Code d'Activation : Une fois inscrit, accédez à l'interface de MailDev via cette URL :
  `http://localhost:1080/#/`
  Recherchez l'email d'activation, et récupérez le code d'activation.
3. Activation du Compte : Accédez à la page d'activation du compte sur le frontend, et entrez le code d'activation pour activer votre compte

## Exécution des Tests

Des tests end-to-end (E2E) sont implémentés avec Cypress.

Pour exécuter les tests E2E : 
  `npm run cypress:open`

Cela ouvrira l'interface de Cypress, où vous pourrez sélectionner et exécuter les tests.

Pour exécuter les tests en mode headless (sans interface graphique) :
  `npm run cypress:run`


---


Ce fichier README inclut toutes les instructions nécessaires pour l'installation, le lancement, la connexion avec des comptes prédéfinis, la création de nouveaux comptes et l'exécution des tests.








   
