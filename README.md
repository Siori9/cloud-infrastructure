# Projet Todo List

## Description 

Le but de ce projet est de permettre à l'utilisateur de se connecter et créer ou retrouver ses Todo lists.

## Architecture

Le projet est composé de trois parties : une partie front-end, une partie back-end et une base de données.
Un dockerfile permet de lancer une instance front-end, deux instances back-end, un reverse proxy pour faire la liaison et la distribution des requêtes et une instance de base de données MongoDB.

## Déploiement 

Pour déployer le projet, il vous suffit de cloner le projet et lancer le docker compose.

Étapes de déploiement :
```
git clone <urlProject> <localFolder>
cd <localFolder>
docker compose up
```
