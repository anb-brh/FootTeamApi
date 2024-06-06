# FootTeamApi

* Guide d'installation
  1. Cloner le projet :
     * git clone <url-du-repo>
        cd foot-team-api
  3. Configurer Maven :
     * Assurez-vous que Maven est installé et configuré correctement.
  4. Construire et exécuter le projet :
     * mvn spring-boot:run
  5. Tester les API :
      1. Obtenir la liste paginée des équipes :
       * curl -X GET "http://localhost:8080/teams?page=0&size=10&sortBy=name"
      2. Ajouter une nouvelle équipe :
       * curl -X POST "http://localhost:8080/teams" -H "Content-Type: application/json" -d '{
    "name": "OGC Nice",
    "acronym": "OGCN",
    "budget": 75000000,
    "players": [
        {"name": "Player 1", "position": "Midfielder"},
        {"name": "Player 2", "position": "Defender"}
    ]
}'
      3. Supprimmer une nouvelle équipe :
      * curl -X DELETE http://localhost:8080/teams/1

      4. Mettre à jour une équipe: 
      * curl -v -X PUT -H "Content-Type: application/json" \
     -d '{"name":"Updated Nice","acronym":"UN","budget":80000000}' \
     http://localhost:8080/teams/1

Choix Techniques : 
- Spring Boot : Pour simplifier le développement d'applications Java.
- Hibernate : Pour gérer la persistance des données.
- H2 Database : Base de données embarquée pour les tests et le développement rapide.
- JUnit et Mockito : Pour les tests unitaires et les tests d'intégration.

Temps Passé :
Le temps total passé sur ce projet est d'environ 6 heures, y compris la configuration du projet, le développement, les tests, et la documentation.
