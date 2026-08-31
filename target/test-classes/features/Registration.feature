Feature: E-Commerce Registration and Order

  Scenario: Register a new user and place an order

    Given I open the registration page
    When I register a new user
    And I login with the registered user
    And I add five products to the cart
    And I select the payment card
    And I select the delivery address
    When I checkout
    Then a tracking number should be generated
    And the order should have status "Processing"