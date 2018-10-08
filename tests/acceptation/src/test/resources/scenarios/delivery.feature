Feature: Delivery

    Background:
        Given an empty delivery deployed on localhost:9100
        And a delivery with id 42 added to the database

    Scenario: Assign a delivery
        Given a delivery with id 56
            And is assigned to Pablo
            And id order is 34
        When the DELIVER action is run
        Then the delivery is registered
            And field deliveryMan is equals to Pablo
            And field idOrder is equals to 34
            And field delivered is equals to false


    #Scenario: Validate a delivery
     #   Given a delivery with id 56
     #       And is assigned to Pablo
     #       And id order is 34
     #   When the VALIDATE action is run
     #   Then the delivery is registered
     #       And field deliveryMan is equals to Pablo
     #       And field idOrder is equals to 34
     #       And field validated is equals to true
