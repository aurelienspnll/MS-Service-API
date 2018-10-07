Feature: Order food

  Background:
    Given an empty orders deployed on localhost:9080
        And a order with id 1 added to the registry


  Scenario: Order a meal

    Given A order identified as 1
        And key nameOfFood set to wings
        And key nameOfClient set to jean
        And key addressDestination set to 22 promenade des anglais
    When the ORDER message is sent
    Then the order is registered
        And key nameOfFood is equals to wings
        And key nameOfClient is equals to jean
        And key addressDestination is equals to 22 promenade des anglais
        And key status is equals to Processing
        And key deliveryTime is given


  Scenario: Validate a order
    Given an id identified as 1
        And set key validate to true
    When the VALIDATE message is sent
    Then order approval is true


  Scenario: Verify that we can consulte all orders
    Given a order with id 2 added to the registry
        And a order with id 3 added to the registry
    When the CONSULT message is sent
    Then there are 3 orders in the registry










