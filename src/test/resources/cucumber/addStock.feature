Feature: Adding a stock to the portfolio
    
   Scenario: Failed to add stock due to empty number of shares
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AAPL" in the ticker field
    And I click "Add Stock"
   	Then I should see the error "Invalid holdings." in the modal
   	
   	
   Scenario: Failed to add stock due to entering 0 shares
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AAPL" in the ticker field
    And I enter "0" for the number of shares
    And I click "Add Stock"
   	Then I should see the error "Zero or negative holdings." in the modal

   	
      	
   Scenario: Failed to add stock due to entering an invalid ticker symbol
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "dsfdf" in the ticker field
    And I enter "3" for the number of shares
    And I click "Add Stock"
   	Then I should see the error "Invalid ticker symbol." in the modal
   	
   	
      	
   Scenario: Successfully add stock to portfolio
    Given I am logged in on the home page
    When I click on the Add Stock dashboard option
    And I enter "AAPL" in the ticker field
    And I enter "3" for the number of shares
    And I click "Add Stock"
   	Then I should see a success message in the modal