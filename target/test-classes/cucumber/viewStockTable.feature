Feature: Checking view stock table functionality
    
   Scenario: Error adding stock to View Stock table due to empty historical ticker symbol
    Given I am logged in on the home page
    When I click on View Stock on my dashboard
    When I click the View Stock button
   	Then I should see the error message above the modal "Not a valid stock ticker. Please try again"
   	
   Scenario: Error adding stock to View Stock table due to invalid ticker symbol
    Given I am logged in on the home page
    When I click on View Stock on my dashboard
    When I enter in "abcdff" for the ticker symbol
    When I click the View Stock button
   	Then I should see the error message above the modal "Not a valid stock ticker. Please try again"
   	
   Scenario: Success adding stock from NASDAQ to View Stocks table
    Given I am logged in on the home page
    When I click on View Stock on my dashboard
    When I enter in "AMZN" for the ticker symbol
    When I click the View Stock button
   	Then I should see "AMZN" in the View Stocks table of the dashboard
   	
   Scenario: Success adding stock from NYSE to View Stocks table
    Given I am logged in on the home page
    When I click on View Stock on my dashboard
    When I enter in "GE" for the ticker symbol
    When I click the View Stock button
   	Then I should see "GE" in the View Stocks table of the dashboard
   	
   Scenario: Success removing NYSE stock from View Stocks table
    Given I am logged in on the home page
    When I click on View Stock on my dashboard
    When I enter in "GE" for the ticker symbol
    When I click the View Stock button
   	When I click on the delete button in the row for GE
   	When I click Delete Stock in the pop-up modal
   	Then "GE" should no longer be in the View Stocks table
   