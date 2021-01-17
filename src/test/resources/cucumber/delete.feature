Feature: Delete Stock Functionality

Scenario: Delete Stock
Given I am logged in and I am on the home page
When I click the button to input CSV data
And I provide a valid CSV file
And I have CSV holdings loaded in
And I click the button for deletion
And I click the button for confirmation of deletion
Then there should be one less stock in the portfolio
