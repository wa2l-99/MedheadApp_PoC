# MedHead Backend

Ce projet est le backend de la plateforme MedHead, une solution de gestion des réservations de lits d'hôpitaux basée sur une architecture microservices. 
Le backend est développé en Java en utilisant Spring Boot.

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés et configurés sur votre machine :

- **Java 17** ou plus récent : Le backend utilise les fonctionnalités de Java 17.
- **Maven** : Pour la gestion des dépendances et la compilation des microservices.
- **Docker** et **Docker Compose** : Pour gérer les services de base de données, Kafka, Zookeeper, et autres composants nécessaires via des conteneurs Docker.
- **Git** : Pour cloner le repository.

## Installation

### 1. **Cloner le repository :**

   ```bash
   git clone git@github.com:wa2l-99/MedheadApp_PoC.git
   cd services
   ```

### 2. **Configurer la base de données et les services auxiliaires :**

Le backend utilise PostgreSQL pour la base de données relationnelle et MongoDB pour certaines données spécifiques.
Un fichier docker-compose.yml est fourni pour lancer les services nécessaires, y compris PostgreSQL, MongoDB, Kafka, Zookeeper, et MailDev.

#### Étapes pour configurer la base de données :

  - Dans cet environnement de développement, nous utilisons une seule source de données PostgreSQL pour héberger les bases de données des différents microservices. Cette approche simplifie la gestion et réduit l'impact sur les ressources du système, tout en offrant une configuration pratique pour le développement local.
  - Le fichier init.sql est utilisé pour initialiser la base de données lors de la création du conteneur PostgreSQL. Il s'assure que la base de données est correctement configurée pour les microservices.

   ```bash
   docker-compose up -d
   ```

Ce script lancera les services suivants :

  - **PostgreSQL** : Héberge la base de données pour les microservices.
  - **pgAdmin** : Interface utilisateur pour gérer PostgreSQL.
  - **MongoDB** : Utilisé par certains microservices pour le stockage non relationnel.
  - **Mongo Express** : Interface utilisateur pour gérer MongoDB.
  - **Zookeeper et Kafka** : Utilisés pour la gestion des messages asynchrones entre microservices.
  - **MailDev** : Simule un serveur de messagerie pour le développement, permettant de vérifier les emails envoyés par l'applicattion.

#### Accéder à la Base de Données :

  - pgAdmin est disponible à l'adresse http://localhost:5050. Connectez-vous avec les identifiants définis dans le fichier docker-compose.yml.
  - Une fois connecté à pgAdmin, créez une nouvelle connexion à la base de données PostgreSQL en utilisant les identifiants définis (wael/wael). Vous verrez que les microservices créent automatiquement leurs bases de données spécifiques lors de leur démarrage.

**Note** : En production, il est recommandé de séparer les bases de données pour chaque microservice pour des raisons de performance, de sécurité, et de maintenance.

### 3. **Lancer les microservices :**

Il est important de démarrer les microservices dans l'ordre suivant :

  - **Config Server :**

    ```bash
    cd config-server
    ./mvnw spring-boot:run
    ```

  - **Discovery Service (Eureka) :**

    ```bash
    cd ../discovery
    ./mvnw spring-boot:run
    ```    
    
  - **User Authentication Service :**

    ```bash
    cd ../userAuthentication
    ./mvnw spring-boot:run
    ``` 

  - **Hospital Management Service :**

    ```bash
    cd ../hospitalManagement
    ./mvnw spring-boot:run
    ``` 

  - **Reservation Service :**

    ```bash
    cd ../reservation
    ./mvnw spring-boot:run
    ``` 

  - **Notification Service :**

    ```bash
    cd ../notification
    ./mvnw spring-boot:run
    ``` 

  - **API Gateway :**

    ```bash
    cd ../gateway
    ./mvnw spring-boot:run
    ``` 

## Exécution des Tests

Les tests unitaires et d'intégration sont principalement implémentés dans le microservice userAuthentication. Pour exécuter les tests :

    ```bash
    ./mvnw test
    ``` 
Pour les autres microservices, les tests sont similaires et seront développés de la même manière.


## Flyway pour la Migration des Données

Flyway est utilisé pour gérer les migrations de la base de données.
Les migrations sont automatiquement appliquées au démarrage des microservices qui interagissent avec PostgreSQL.

## Swagger/OpenAPI
Une documentation Swagger est disponible pour chaque microservice. Après le démarrage du projet, vous pouvez accéder à la documentation via l'URL suivante : 

    ```bash
    http://localhost:8222/webjars/swagger-ui/index.html
    ``` 

---

Ce README est structuré pour guider l'utilisateur à travers l'installation, la configuration, le démarrage et les tests du backend du projet MedHead.






