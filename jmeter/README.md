# Tests de Performance avec JMeter

Ce dossier contient le fichier de tests de performance pour la plateforme MedHead, réalisés avec Apache JMeter. 
Ces tests visent à évaluer la montée en charge et la performance des microservices de l'application.

## Prérequis

Avant de lancer les tests, assurez-vous que les éléments suivants sont installés et configurés :

- **Apache JMeter** : JMeter doit être installé sur votre machine.
- **Backend MedHead** : Assurez-vous que tous les microservices backend sont démarrés et fonctionnent correctement.

### Installation de JMeter

1. **Téléchargement :**
   - Téléchargez JMeter depuis la [page de téléchargement officielle](https://jmeter.apache.org/download_jmeter.cgi).

2. **Installation :**
   - Extrayez l'archive téléchargée dans un dossier de votre choix.

3. **Vérification :**
   - Ouvrez JMeter pour vous assurer qu'il fonctionne correctement.

## Exécution des Tests

### Étape 1 : Assurez-vous que le Backend est en Cours d'Exécution

Avant de commencer les tests, vérifiez que tous les microservices du backend sont en cours d'exécution et opérationnels.
Cela garantit que les tests de performance se dérouleront correctement.

### Étape 2 : Ouvrir JMeter

1. **Lancer JMeter :**

   - Naviguez vers le dossier où vous avez installé JMeter.
   - Exécutez l'application JMeter (`jmeter.bat` pour Windows, ou `jmeter` pour macOS/Linux).

2. **Importer le Fichier de Test :**

   - Une fois JMeter ouvert, allez dans le menu **File** (Fichier) et sélectionnez **Open...** (Ouvrir...).
   - Naviguez vers le dossier `jmeter` de ce projet et sélectionnez le fichier de test `medhead_poc_charge_test.jmx`.

3. **Lancer les Tests :**

   - Après avoir importé le fichier de test, vous pouvez lancer les tests en cliquant sur le bouton **Start** (Démarrer) dans la barre d'outils de JMeter (icône verte en forme de flèche).
   - Surveillez les résultats en temps réel dans les différentes vues de JMeter (comme **View Results Tree** et **Summary Report**).

## Conseils pour l'Exécution des Tests

- **Assurez-vous que le backend est stable** : Avant de lancer les tests, vérifiez que tous les microservices sont en cours d'exécution et fonctionnent sans erreurs.

---

Ce README fournit des instructions claires pour lancer les tests de performance en utilisant l'interface graphique de JMeter, en important simplement le fichier de test et en exécutant les tests.
Il assure également que les microservices du backend doivent être en cours d'exécution avant de commencer les tests.
