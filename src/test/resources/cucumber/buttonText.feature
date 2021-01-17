Feature: Checking button text on homepage
    
   Scenario: Verify button text in Upload Portfolio modal
    Given I am logged in on the home page
    When I click on Upload Portfolio on my dashboard
   	Then I should see the buttons "Upload File" and "Cancel"
   	
   	
	Scenario: Verify button text in Add Stock modal
    Given I am logged in on the home page
    When I click on Add Stock on my dashboard
   	Then I should see buttons with text "Add Stock" and "Cancel"
   	
   		
	Scenario: Verify button text in View Stock modal
    Given I am logged in on the home page
    When I click on View Stock on my dashboard
   	Then I should see the following buttons "View Stock" and "Cancel"
   	
   	Scenario: Verify button text in Confirm Deletion
   	Given I am logged in on the home page
    When I click on the delete button in the row of user stocks
   	Then I should see the buttons with "Delete Stock" and "Cancel"
   	
   	
   	Scenario: Verify Date purchased and Date sold fields in Add Stock modal
    Given I am logged in on the home page
    When I click on Add Stock on my dashboard
   	Then I should see two fields "Date Purchased" and "Date Sold (optional)"
   	
   	Scenario: Verify Select All and Deselect All buttons in My Stocks Table
   	Given I am logged in on the home page
   	Then I should see the buttons "Select All" and "Deselect All" in the table header
   	
   	Scenario: Verify button text in Confirm Deletion for View Stocks Table
   	Given I am logged in on the home page
   	When I view the stock "AMZN"
    When I click on the delete button in the row of view stocks
   	Then I should see the a pop-up with the buttons "Delete Stock" and "Cancel"
   	