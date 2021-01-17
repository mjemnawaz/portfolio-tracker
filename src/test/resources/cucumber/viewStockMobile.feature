Feature: Checking view stock table functionality on mobile device
   	
Scenario: Success adding and removing NYSE stock from View Stocks table on mobile device
Given I am logged in on the home page on mobile device
When I click on View Stock on my dashboard on mobile
When I enter in "GE" for the ticker symbol on mobile
When I click the View Stock button on mobile
When I click on the delete button in the row for GE on mobile
When I click Delete Stock in the pop-up modal on mobile
Then "GE" should no longer be in the View Stocks table on mobile
   