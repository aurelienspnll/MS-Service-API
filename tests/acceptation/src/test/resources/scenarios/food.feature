Feature: Food Catalogue

  Background:
    Given an empty food catalogue deployed on localhost:9090
    And a food named Cheese Burger added to the catalogue
    And a food named Nachos added to the catalogue

  Scenario: Registering a food
    Given A food named Classic Pancakes
    And with description  set to The homey goodness of plain golden pancakes served with whipped butter
    And with price        set to 4.25
    When the post method is used
    Then the food is registered
    And the name        is equals to Classic Pancakes
    And the description is equals to The homey goodness of plain golden pancakes served with whipped butter
    And the price       is equals to 4.25
    And there are 3 food items in the catalogue


  Scenario: Getting all food items
    When the get method is used
    Then the answer contains 2 results