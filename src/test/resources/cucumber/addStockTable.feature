Feature: Adding a stock to the My Stocks table
          	
   Scenario: Error adding stock to portfolio due to empty date purchased field
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AAPL" in the ticker field
    And I enter "3" for the number of shares
    And I click the "Add Stock" button below
   	Then I should see an error message in the modal "Buy date cannot be empty."
   	
   	
   Scenario: Error adding stock to portfolio due to date sold earlier date purchased
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AAPL" in the ticker field
    And I enter "3" for the number of shares
    And I enter "10252020" for date purchased
    And I enter "10182020" for date sold
    And I click the "Add Stock" button below
   	Then I should see an error message in the modal "Buy date cannot be after sell date."
   	
   Scenario: Success adding NASDAQ stock AMZN to My Stocks table with date purchased a year ago
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AMZN" in the ticker field
    And I enter "1" for the number of shares
    And I enter "11072020" for date purchased
    And I click the "Add Stock" button in the modal
   	Then I should see "AMZN" in the My Stocks table below
   	
   Scenario: Success adding NYSE stock GE to My Stocks table with date purchased a year ago and sold yesterday
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "GE" in the ticker field
    And I enter "1" for the number of shares
    And I enter "11072019" for date purchased
    And I click the "Add Stock" button in the modal
   	Then I should see "GE" in the My Stocks table below
   	
   	
   Scenario: Success adding NASDAQ stock AAPL to My Stocks table then removing it
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AAPL" in the ticker field
    And I enter "1" for the number of shares
    And I enter "11032020" for date purchased
    And I click the "Add Stock" button in the modal
    And I select the Delete button in the AAPL row of My Stocks
    And I press Delete Stock inside the Delete Modal
   	Then I should not see "AAPL" in the My Stocks table anymore
   	