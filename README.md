# MS-Service-API Ubero

## 1. Environement

-Être sous linux/mac  
-Avoir java JDK 1.8 ou plus  installé  
-Avoir maven d'installé >> sudo apt-get install maven  
-Avoir docker d'installé (voir le tuto >> https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/)  
-Avoir docker-compose d'installé (voir le tuto >> https://docs.docker.com/compose/install/#install-compose)  


## 2. Executer le programme

Vérifier que le service docker est bien lancé :
 linux :  (service --status-all)
  mac : la baleine dans la bare des taches 
  
si ce n'est pas le cas : sudo service docker start


- A la racine du projet, exécuter la commande : ./install.sh pour créer les images docker  
- Ensuite exécuter la commande ./run.sh pour lancer les services.

 ## 3. Services
 
  ### 3. Service document orderFood (pour commander du KFC)
  
  installer un logiciel pour tester les requetes tel que Postman: https://www.getpostman.com/apps


- Post   http://localhost:9080/order-service-document/ordersFood
- Body -> raw -> application/json

Requête: pour faire une commande

{ "event": "ORDER", "orderFood": { "id":"23", "nameOfFood":"sandwitch KFC", "nameOfClient":"toto", "addressDestination":"3 avenue promenade des anglais"}
}

Requête: pour valider une commande

{ "event": "VALIDATE", "id":"23" , "validate":true}

Requête: pour avoir toutes les commandes

{ "event": "CONSULT"}

Requête: pour purger la bd

{ "event": "PURGE"} 

### 3. Service ressource catalogue de repas


Requete: pour ajouter des repas dans le catalogue

- Post   http://localhost:9090/catalogue-service-rest/meals
- Body -> raw -> application/json

{ "name":"tarte au pomme"  "description":"tarte avec de la pomme et du caramel" "price":"3" }

Requete : pour afficher le catalogue :

- Get   http://localhost:9090/catalogue-service-rest/meals

On peut filtrer les repas qui sont dans nos moyens en ajoutant le queryParam  maxPrice pour avoir tous les repas qui ont un pris <= à la valeur mise :

- Get  http://localhost:9090/catalogue-service-rest/meals?maxPrice=30  

ici on a tous les repas <= 30 euros

### 3. Service de livraison






## 4. Docker 

Docker permet d’exécuter nos services et des services extérieurs à l’intérieur de conteneurs.
Sachant que chaque conteneur vit sa vie et gère son propre environnement d’exécution, cela
nous permet d’avoir chaque service conteneurisé comme une boite noire.

### Commandes utiles :

voir les containers docker :
- docker ps

arreter et supprimer les containers docker :
 - docker stop $(docker ps -a -q)
 - docker rm $(docker ps -a -q)

 


