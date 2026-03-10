Feature: Car Rental

  Scenario: Add a new car
    Given the car rental manager is empty
    When I add a new car with make "Toyota" and model "Corolla"
    Then the list of cars should have 1 car

  Scenario: Rent a car
    Given the car rental manager has a car with make "Toyota" and model "Corolla"
    When I rent the car
    Then the car should be unavailable

  Scenario: Return a car
    Given the car rental manager has a rented car with make "Toyota" and model "Corolla"
    When I return the car
    Then the car should be available