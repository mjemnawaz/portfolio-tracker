Feature: logout

Scenario: Making sure logout redirects user back to login page
Given I am on the user registration page
When I sign up and sign in with a user
And I click logout
Then I should be back to login page