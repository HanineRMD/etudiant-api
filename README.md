# Projet Gestion des Étudiants

## Prérequis
- Docker et Docker Compose
- Flutter SDK
- Android Studio / VS Code

## Installation et Lancement

### 1. Lancer l'API et la base de données avec Docker

bash
docker compose up --build

1. Configuration initiale
bash
git checkout -b version-2

cd C:\Users\Hanin\Downloads\projet-etudiants

2. Build et déploiement Docker

bash

docker-compose up --build -d

curl http://localhost:8081/api/etudiants

3. Build image Docker

bash

docker build -t hanineramdhane/etudiant-service:1.0 ./api-spring-boot/etudiant-api

docker push hanineramdhane/etudiant-service:1.0

4. Déploiement Kubernetes

bash

kubectl apply -f k8s/postgres-deployment.yaml

kubectl apply -f k8s/etudiant-deployment.yaml

kubectl get pods

kubectl port-forward service/etudiant-api 8081:8080

5. Commandes utiles

bash

docker-compose logs -f api        # Voir les logs

docker-compose down -v            # Arrêter et supprimer les volumes

kubectl get all                   # Voir tous les ressources K8s

mvn test                          # Exécuter les tests

6. Tests API

bash

# GET tous les étudiants

curl http://localhost:8081/api/etudiants



# POST nouvel étudiant

curl -X POST http://localhost:8081/api/etudiants -H "Content-Type: application/json" -d "
{\"cin\":\"C999\",\"nom\":\"Test\",\"dateNaissance\":\"2000-01-
01\",\"email\":\"test@test.com\",\"anneePremiereInscription\":2020}"


# DELETE étudiant

curl -X DELETE http://localhost:8081/api/etudiants/1

7. Accès applications

API Swagger: http://localhost:8081/swagger-ui.html

Interface web: http://localhost:8081/index.html

Flutter Mobile: http://10.0.2.2:8081 (émulateur) ou http://10.98.129.39:8081
