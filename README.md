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

Header: Content-type: application/json
Body -> raw -> application/json
Post   http://localhost:9080/order-service-document/orderFood

Requête:
{ "event": "ORDER", "orderFood": { "id":"23", "nameOfFood":"sandwitch KFC", "nameOfClient":"toto", "addressDestination":"3 avenue promenade des anglais"}
}

Requête:
{ "event": "VALIDATE", "id":"23" , "validate":true}

Requête:
{ "event": "CONSULT"}

