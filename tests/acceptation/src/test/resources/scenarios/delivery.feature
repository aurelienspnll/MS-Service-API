Feature: Delivery

    Background:
        Given an empty delivery deployed on localhost:9100
        And a delivery with id 42 added to the registry

    Scenario: Assign a delevery
        Given a delivery identified as 56
            And is assigned to Pablo
            And id order is 34
        When the DELIVERY action is run
        Then the delivery is registered
            And the field deliveryMan has its value equals to Pablo
            And the field idOrder has its value equals to 34
            And the field validated has its value equals to false
