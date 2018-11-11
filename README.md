# MS-Service-API Ubero

## 1. Environement

- Être sous linux/mac  
- Avoir java JDK 1.8 ou plus installé  
- Avoir maven d'installé >> sudo apt-get install maven  
- Avoir docker d'installé https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/  
- Avoir docker-compose d'installé https://docs.docker.com/compose/install/#install-compose


## 2. Executer le programme

### 2.1 Vérifier que le logiciel docker est bien lancé
 - linux :  service --status-all
 - mac : la baleine dans la barre des tâches
  
si ce n'est pas le cas : 
 - linux : sudo service docker start
 - mac : Finder -> Application -> Docker
 
 
### 2.2 Lancer les services

- A la racine du projet, exécuter la commande pour créer les images docker: ./install.sh 
- Ensuite exécuter la commande pour créer les containers: docker-compose -d up
- Finalement la commande pour lancer les tests de stress et d'acceptation: ./run.sh


 ## 3. Services
 
  ### 3.1 Service document orderFood (pour commander du KFC)
  
  Installer un logiciel pour tester les requêtes telles que Postman: https://www.getpostman.com/apps


- Post   http://localhost:9080/order-service-document/ordersFood
- Body -> raw -> application/json

L'objet JSON dans le body de la Requête pour faire une commande:
```json
{ 
  "event": "ORDER",
  "orderFood": { 
    "id":"23", 
    "nameOfFood":"Hot Wings 30 Bucket", 
    "nameOfClient":"Aurélien", 
    "addressDestination":"3 avenue promenade des anglais"
  }
}
```

L'objet JSON dans le body de la Requête pour valider une commande:
```json
{"event": "VALIDATE", "id":"23", "validate":true}
```

L'objet JSON dans le body de la Requête pour refuser une commande:
```json
{"event": "VALIDATE", "id":"23", "validate":false}
```

L'objet JSON dans le body de la Requête pour avoir toutes les commandes:
```json
{ "event": "CONSULT"}
```
L'objet JSON dans le body de la Requête pour purger la base de données:
```json
{ "event": "PURGE"} 
```


### 3.2 Service ressource catalogue de repas

La Requête pour ajouter des repas dans le catalogue :

- Post   http://localhost:9090/food-service-rest/foods
- Body -> raw -> application/json

L'objet JSON dans le body:
```json
{
 "food":
   {
     "name": "tarte aux pommes",
     "description": "Tarte sucrée, faite d'une pâte feuilletée garnie de pommes émincées.",
     "category": "dessert",
     "price": 3.5
   }
}
```
La Requête pour afficher le catalogue :

- Get  http://localhost:9090/food-service-rest/foods

La Requête pour afficher le catalogue filtré par catégories:

- Get  http://localhost:9090/food-service-rest/foods?cat=asian



### 3.3 Service de livraison

- Post   http://localhost:9100/delivery-service-document/delivery
- Body -> raw -> application/json

L'objet JSON dans le body de la Requête pour créer une livraison et l'assigner:
```json
{
"event" : "DELIVER",
    "delivery" : {
       "id": "12",
       "order": {
        "idOrder" : "36",
        "idClient" : "65",
        "clientName" : "Tata",
        "address" : "Rue de Medeline"
       },
    "deliveryMan": {
        "idDeliveryMan" : "2",
        "firstName": "Pablo",
        "lastName" : "Escobar"
       },
    "delivered": false
    }
}
```

L'objet JSON dans le body de la Requête pour completer une livraison:
```json
{"event": "COMPLETE", "id":"6"}
```

L'objet JSON dans le body de la Requête pour consulter une livraison:
```json
{"event": "CONSULT", "id":"6"}
```

L'objet JSON dans le body de la Requête pour avoir toutes les livraisons:
```json
{"event": "LIST"}
```

L'objet JSON dans le body de la Requête pour avoir toutes les livraisons terminées:
```json
{"event": "LISTCOMPLETED"}
```

L'objet JSON dans le body de la Requête pour avoir toutes les livraisons non terminées:
```json
{"event": "LISTNOTCOMPLETED"}
```

L'objet JSON dans le body de la Requête pour supprimer une livraison:
```json
{"event": "DELETE", "id":"6"}
```

L'objet JSON dans le body de la Requête pour suivre une livraison:
```json
{"event": "TRACK", "id":"6"}
```

L'objet JSON dans le body de la Requête pour signaler un problème avec une livraison:
```json
{"event": "PROBLEM", "id":"6"}
```



## 4. Choix de conception pour les différents services

### 4.1 Service Catalogue: 

Nous avons choisi d'utiliser le paradigme *Resource* comme interface du service de catalogue de repas.
Le catalogue est une ressources qui contient des repas auquel on pourrai y accéder par une URL : 
http://localhost:9090/food-service-rest/foods. 
Ce paradigme nous offre une grande modularité: 

- On pourrait ajouter un autre chemins très aisément afin de compléter ce service de catalogue comme par exemple :
hhttp://localhost:9090/food-service-rest/foods/id_food pour afficher le detail d'un repas.
 
 
### 4.2 Service de commande :

Nous avons choisi d'utiliser le paradigme *Document* comme interface du service de commande. Il existe beaucoup de cas utilisations pour ce service :

- le client fait une commande et un temps de livraison lui est transmis 
- Le client valide ou pas la commande si le temps de livraison lui convient ou pas
- Le cuisinier doit pouvoir consulter la liste des commandes

Tous ces cas utilision se prète bien à des évènement tel que : 
 - ORDER pour faire la commande,
 - VALIDATE pour valider ou pas une commande,
 - CONSULT pour consulter la liste des commandes
 
Ce paradigme nous permet ,à travers des évènements, de gérer tous ces cas utilisations avec la même route: http://localhost:9080/order-service-document/ordersFood.


### 4.3 Service de livraison :

Dans un premier temps, nous étions parti sur le paradigme RPC pour la livraison car nous pensions qu'une livraison se résumait à la procédure: *livrer(repas, destination)* mais nous nous sommes rendu compte qu'une livraison débute lorsque l'on entre dans le system l'objet à livrer et se finir lorsque le client se fait livrer le repas. De ce fait, il y a là plusieurs cas utilisation :

- Assigner à un livreur le repas à livrer.
- Le livreur doit ensuite mettre dans le system la preuve que la livraison a été faite : facture du client.
- Le patron peut consulter l'état des livraisons en cas de litige par exemple.

Tous ces cas utilision se prète bien à des évènement tel que :
 - DELIVER pour ajouter une livraison et l'assigner à un livreur.
 - COMPLETE pour marquer la fin d'une livraison et y ajouter la facture.
 - CONSULT pour consulter une livraison en particulier.
 - LIST pour consulter l'état des livraisons.
 - LISTCOMPLETED pour consulter les livraisons effectuées.
 - LISTNOTCOMPLETED pour consulter les livraisons en cours.
 - DELETE pour supprimer les livraisons

Ce paradigme nous permet de gérer tous ces cas utilisations avec des évènements sur la route: 
http://localhost:9100/delivery-service-document/delivery


## 5. Tests

### 5.1 Environement 

Attention pour faire les tests de charge, il faut une version de java compatible avec notre version de gatling et scala (Il existe des problèmes avec java 9 du coup:  utiliser java 1.8 pour lancer les tests de charges)

### 5.2 Lancer les tests (scenario + charge)

- Lancer les services (./install.sh puis docker-compose up -d) 
- Lancer ./run.sh


## 6. Docker 

Docker permet d’exécuter nos services et des services extérieurs à l’intérieur de conteneurs.
Sachant que chaque conteneur vit sa vie et gère son propre environnement d’exécution, cela
nous permet d’avoir chaque service conteneurisé comme une boite noire.

### 6.1 Commandes utiles :

- Afficher les containers docker :
    - docker ps

- Arreter et supprimer les containers docker :
    - docker stop $(docker ps -a -q)
    - docker rm $(docker ps -a -q)
    
    
## Equipe

- Mohamed Chennouf
- Andreina Wilhelm
- Aurélien Spinelli

 


