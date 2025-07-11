# tenisu-api
Java / Spring Tennis Stats API


tenisu-api API REST simple en Java 21 et Spring Boot 3 qui expose des statistiques de joueurs de tennis.

---

Fonctionnalités

- Documentation interactive via Swagger 
- Déploiement local et sur AWS (ECS Fargate)

Utilisation en local

1. Lancer l'application

Assurez-vous que Java 21 et Maven (3.9.9 pour mon cas)  sont installés puis exécutez dans la racine du projet :

./mvnw spring-boot:run

Ou bien lancer avec le jar :

./mvnw clean package -DskipTests
java -jar target/tenisu-api-0.0.1-SNAPSHOT.jar

2. Tester L'API en local :

- Endpoint principal : http://localhost:8080/api/players

- Documentation Swagger:
http://localhost:8080/swagger-ui/index.html

3. Accéder à l'application déployée sur AWS ( ECS Fargate )

L’API est aussi déployée sur ECS, accessible publiquement : http://tenisu-alb-457568053.eu-west-3.elb.amazonaws.com/api/players

- Retourner la liste des joueurs triés par nombre de victoires : 
  http://tenisu-alb-457568053.eu-west-3.elb.amazonaws.com/api/players

- Retourner les informations d’un joueur grâce à son ID :
  Exemple : ttp://tenisu-alb-457568053.eu-west-3.elb.amazonaws.com/api/players/65

- Retourner les statistiques des joueurs ( Pays qui a le plus grand ratio de parties gagnées - IMC moyen de tous les joueurs - La médiane de la taille des joueurs ) :
  http://tenisu-alb-457568053.eu-west-3.elb.amazonaws.com/api/stats

- Ajouter un nouveau joueur ( POST ) :

   curl -X POST http://tenisu-alb-457568053.eu-west-3.elb.amazonaws.com/api/players \
        -H "Content-Type: application/json" \
        -d '{
          "firstname": "Amine",
          "lastname": "Madani",
          "shortname": "A.MAD",
          "sex": "M",
          "picture": "https://tenisu.latelier.co/resources/Nadal.png",
          "country": {
            "code": "ESP",
            "picture": "https://tenisu.latelier.co/resources/USA.png"
          },
          "data": {
            "rank": 1,
            "points": 9999,
            "last": [1, 1, 1, 1, 1], // ;)
            "age": 26,
            "height": 175,
            "weight": 75,
          }
        }'

-Tester l'API plus facilement via Swagger ! http://tenisu-alb-457568053.eu-west-3.elb.amazonaws.com/swagger-ui/index.html
<img width="1759" height="757" alt="image" src="https://github.com/user-attachments/assets/afd04513-3c3a-4ac2-a0e7-6ee5a07d374f" />
