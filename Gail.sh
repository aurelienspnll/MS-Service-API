#!/usr/bin/env bash

echo "Scenario 1: Gail une étudiante une pizza sur uberoo"

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
echo "*******1- Gail parcour le catalogue "
echo "Press any key to continue..."

echo ""
echo ""
curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:9090/food-service-rest/foods
echo ""
echo ""

echo "*******1- Gail ajoute sa commande "
echo "Press any key to continue..."
read


curl -X POST -H "Content-Type: application/json" -d "{
  \"event\": \"ORDER\",
  \"orderFood\": {
    \"id\":\"2\",
    \"nameOfFood\":\"Pizza norvégiène\",
    \"nameOfClient\":\"Gail\",
    \"addressDestination\":\"3 avenue promenade des anglais\"
  }
}" http://localhost:9080/order-service-document/ordersFood

echo ""
echo ""
echo "Un event kafka est envoyé chez Eta"

curl -X POST -H "Content-Type: application/json" -d "{
  \"posX\": \"22\",
  \"posY\": \"11\",
}" http://localhost:9070/eta-service-rpc/CalculateEta


echo ""
echo ""
echo "Gail valide sa commande"
echo "Press any key to continue..."
read

echo ""
echo ""

curl -X POST -H "Content-Type: application/json" -d "{\"event\": \"VALIDATE\", \"id\":\"2\", \"validate\":true}" http://localhost:9080/order-service-document/ordersFood


echo ""
echo ""
echo "Un event kafka est envoyé chez Delivery"

curl -X POST -H "Content-Type: application/json" -d "{
  \"event\" : \"DELIVER\",
  \"delivery\" : {
    \"deliveryMan\": \"Gail\",
    \"delivered\": \"false\",
    \"id\": \"33\",
    \"idOrder\": \"2\"
    }
}" http://localhost:9100/delivery-service-document/delivery

echo ""
echo ""
echo "Un event kafka est envoyé chez Bank"

curl -X POST -H "Content-Type: application/json" -d "{
  \"event\" : \"ADDPAYMENT\",
  \"payment\" : {
    \"price\": \"12\",
    \"id\": \"Gail\"
    }
}" http://localhost:9120/bank-service-document/payment

echo ""
echo "Le payement de Gail est  accepté"
echo "Press any key to continue..."
read
echo "{
            \"price\": 12,
            \"id\": \"5be851ee2b659900012f6186\",
            \"status\": \"approved\"
        }"

echo ""
echo ""

echo "Jordan le chef du restaurant consulte ces commandes"
echo "Press any key to continue..."
read

