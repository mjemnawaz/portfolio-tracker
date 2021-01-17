Feature: CSV Functionality

Scenario: Provide data to account with invalid CSV file
Given I am logged in and I am on the home page
When I click the button to input CSV data
And I provide an invalid CSV file
Then I should see an error message for nonexistent file

Scenario: Provide data to account with valid CSV file with incorrect data
Given I am logged in and I am on the home page
When I click the button to input CSV data
And I provide a valid CSV file with invalid data
Then I should see an error message for incorrect or empty data

Scenario: Provide data to account with valid CSV file with empty data
Given I am logged in and I am on the home page
When I click the button to input CSV data
And I provide a valid CSV file with empty data
Then I should see an error message for incorrect or empty data

Scenario: Provide data to account with valid CSV file
Given I am logged in and I am on the home page
When I click the button to input CSV data
And I provide a valid CSV file
Then I should see the data from my CSV file on my home page