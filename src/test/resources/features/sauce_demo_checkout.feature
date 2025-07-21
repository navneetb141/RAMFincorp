Feature: SauceDemo E2E Checkout Flow

  Scenario: Complete an order and logout
    Given User launches the SauceDemo website
    When User log in to the application
    Then User should be navigated to the inventory page

    When User adds Sauce Labs Backpack and Sauce Labs Bolt T-Shirt to the cart
    And User navigates to the cart
    Then Both products should be present in the cart with quantity 1

    When User proceeds to checkout with first name "John", last name "Doe", and zip "12345"
    Then User should see item total, tax, and total as sum of item total and tax

    When User clicks Finish
    Then Confirmation message "Thank you for your order!" should be displayed

    When User logs out from the product page
    Then User should be navigated back to the login page
