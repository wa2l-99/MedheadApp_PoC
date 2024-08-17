# MedHead Platform - Proof of Concept

Ce dépôt contient le Proof of Concept (PoC) de la plateforme MedHead,
une solution de gestion des réservations de lits d'hôpitaux développée avec une architecture microservices. 
Le projet est divisé en deux principales parties : le frontend développé en Angular, et le backend composé de plusieurs microservices développés en Spring Boot.

## Structure du Projet

- **poc-medhead-frontend/** : Contient le code source du frontend développé en Angular.
- **services/** : Contient les différents microservices backend.
- **jmeter/** : Contient les fichiers de tests de performance et de montée en charge.
- **.github/** : Contient les fichiers de configuration pour les pipelines CI/CD du backend et du frontend.

## Prérequis

Avant de commencer, assurez-vous d'avoir installé les outils suivants :

- **Java 17** ou plus récent
- **Maven**
- **Node.js** et **Angular CLI**
- **Docker** et **Docker Compose**
- **Git**
- **Apache JMeter** : Pour exécuter les tests de performance. JMeter doit être installé et configuré dans votre `PATH` pour pouvoir être lancé depuis la ligne de commande.

## Lancer le Projet

### Backend

Pour configurer et lancer les microservices, référez-vous au fichier README.md situé dans le dossier `services/`.

### Frontend

Pour configurer et lancer le frontend, référez-vous au fichier README.md situé dans le dossier `poc-medhead-frontend/`.

## Tests de Performance avec JMeter

Pour lancer les tests de performance avec JMeter, référez-vous au fichier README.md situé dans le dossier `jmeter/`. Vous y trouverez les instructions pour importer et exécuter les tests.

## Configuration des Pipelines CI/CD

### Pipelines GitHub Actions

  **1- Backend Pipeline :**
  
  Le pipeline pour le backend est configuré dans le fichier .github/workflows/backend-pipeline.yml. Il compile les microservices, exécute les tests unitaires et d'intégration, et prépare les artefacts pour un éventuel déploiement.
  
  **2- Frontend Pipeline :**
  
  Le pipeline pour le frontend est configuré dans le fichier .github/workflows/frontend-pipeline.yml. Il installe les dépendances, exécute les tests end-to-end (E2E) avec Cypress, et génère le build de l'application Angular.

### Exécution des Pipelines

Les pipelines CI/CD sont déclenchés automatiquement à chaque push request sur les branches du dépôt. Vous pouvez également les déclencher manuellement via l'interface GitHub si nécessaire.
