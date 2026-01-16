## Réponses — Exercice 7

Voici les observations réalisées en lançant, depuis la racine du projet (`bank-application`), les commandes demandées :

### 1) `mvn clean`
- Phases exécutées : lifecycle "clean" — exécution du goal `maven-clean-plugin:clean` (phase `clean`).
- Effet sur `target/` : le répertoire `target/` est supprimé. Après `mvn clean` il n'y a plus d'artéfacts dans `target/`.

### 2) `mvn test`
- Phases exécutées : lifecycle par défaut jusqu'à la phase `test`. Concrètement : `validate`, `compile`, `test-compile`, `test` (plugins liés à ces phases sont invoqués — compilation des sources et des tests puis exécution des tests unitaires via Surefire).
- Nouveaux fichiers / dossiers sous `target/` observés après l'exécution :
  - `classes/` — classes compilées (main)
  - `test-classes/` — classes compilées (tests)
  - `generated-sources/`
  - `generated-test-sources/`
  - `maven-status/`
  - `surefire-reports/` — rapports de tests (fichiers `.txt` et `.xml` générés par Surefire)

### 3) `mvn package`
- Phases exécutées : lifecycle par défaut jusqu'à la phase `package`. (Inclut `validate`, `compile`, `test-compile`, `test` puis `package`.) Note : les tests sont exécutés avant le packaging sauf si on les skippe.
- Nouveaux fichiers / dossiers sous `target/` observés après l'exécution :
  - `bank-application-1.0-SNAPSHOT.jar` — l'artéfact JAR produit par la phase `package`
  - `maven-archiver/` — métadonnées d'archivage (pom.properties, etc.)
  - (Les dossiers créés par `mvn test` restent présents : `classes/`, `test-classes/`, `surefire-reports/`, ...)

### 4) `mvn verify`
- Phases exécutées : lifecycle par défaut jusqu'à la phase `verify`. Cela couvre : `validate`, `compile`, `test-compile`, `test`, `package`, `integration-test`, `verify` (tous les goals/plugins liés à ces phases sont invoqués si configurés).
- Nouveaux fichiers / dossiers sous `target/` observés après l'exécution :
  - Dans ce projet, `mvn verify` n'a pas créé de nouveaux fichiers par rapport à `mvn package` — le JAR et les mêmes dossiers sont présents.

### Hypothèse / explication : en quoi `verify` diffère de `test` et `package` ?
- `test` : exécute les tests unitaires (phase `test`) après compilation. Ne produit pas l'artéfact final.
- `package` : produit l'artéfact (jar/war) dans la phase `package`; la phase inclut l'exécution des tests unitaires avant le packaging.
- `verify` : va plus loin — elle exécute des étapes de vérification post-packaging comme des tests d'intégration, des validations ou contrôles qui nécessitent l'artéfact créé (phases `integration-test` puis `verify`).
- Dans un projet où des plugins (ex. Failsafe pour les tests d'intégration) ou des vérifications sont liés aux phases `integration-test` / `verify`, `mvn verify` lancera ces jobs supplémentaires. Dans ce dépôt précis il n'y a pas (ou il n'y a pas eu d'effet observable) de tasks supplémentaires liées à `integration-test` / `verify`, d'où l'absence de nouveaux fichiers après `mvn verify`.

### Conclusion courte
- Commandes et rôles : `clean` supprime `target/`; `test` compile et exécute les tests unitaires; `package` crée le JAR; `verify` exécute ensuite les vérifications post-package (si configurées).
- Résultat pratique sur ce projet : après `mvn package` on obtient `target/bank-application-1.0-SNAPSHOT.jar`; `mvn verify` n'a rien ajouté de visible ici.

Si vous voulez, j'ajoute dans le `pom.xml` un exemple d'intégration avec le plugin Failsafe pour démontrer la différence `package` vs `verify` (je peux créer un petit test d'intégration d'exemple et ré-exécuter `mvn verify`).
