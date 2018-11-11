#!/usr/bin/env bash

echo "Scenario 2: Terry le restaurateur"

echo ""

echo "----Creation du contexte ----"

echo "Ajout d'une pizza dans le catalogue"
# Ajout d'un repas pizza dans le catalogue

curl -X POST -H "Content-Type: application/json" -d "{
 \"food\":
   {
     \"name\": \"pizza norvegiene\",
     \"description\": \"la pizza de chuck norris.\",
     \"price\": 6,
     \"category\":\"fastfood\"
   }
}" http://localhost:9090/food-service-rest/foods


echo "--- Context created ---"

# ************** Scenario **************
echo "Jordanie consulte les repas"
echo "Press any key to continue..."

echo ""
echo ""
curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9090/food-service-rest/foods
echo ""
echo ""

echo "Jordanie ajoute des commentaire au repas   "
echo "Press any key to continue..."
read


curl -X POST -H "Content-Type: application/json" -d "{	\"event\" : \"FEEDBACK\",
	\"feedback\" : {
		\"client\" : {
			\"id\" : \"2\",
			\"firstName\" : \"Luigi\",
			\"lastName\" : \"DeLaVega\"
		},
		\"food\" : {
			\"id\" : \"56\",
			\"name\" : \"Pizza\",
			\"category\" : \"Italien\"
		},
		\"view\" : \"La pate est pas mal\"
	}
}" http://localhost:9130/feedback-service-document/feedback



echo ""
echo ""
echo "Gail et Erin veulent suivre la géoloque de leurs coursier"
read

echo "Ajout de la commande"


curl -X POST -H "Content-Type: application/json" -d "{\"event\" : \"DELIVER\",
         \"delivery\" : {
            \"id\": \"6\",
            \"order\": {
            	\"idOrder\" : \"2\",
            	\"idClient\" : \"65\",
            	\"clientName\" : \"Gail\",
            	\"address\" : \"Rue de Medeline\"

            },
            \"deliveryMan\": {
            	\"idDeliveryMan\" : \"2\",
            	\"firstName\": \"Tomas\",
            	\"lastName\" : \"Escobar\"
            },
            \"delivered\": false
         }
}" http://localhost:9100/delivery-service-document/delivery


echo "Track la commande"
read

curl -X POST -H "Content-Type: application/json" -d "{\"event\": \"TRACK\", \"id\":\"6\"}" http://localhost:9100/delivery-service-document/delivery



echo "Terry souhaite obtenir des statistiques sur la rapidité et le cout des delais de livraison"
read
echo "Pas fait"


echo "Jamie a un accident et tiens informer sa boite pour etre remplacé"
read

curl -X POST -H "Content-Type: application/json" -d "{\"event\": \"PROBLEM\", \"id\":\"6\"}" http://localhost:9100/delivery-service-document/delivery
