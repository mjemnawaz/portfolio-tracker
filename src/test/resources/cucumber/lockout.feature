Feature: lockout

Scenario: Ensuring user locked out after 3 consecutive login failures
Given I am on the login page
When I input user information with an username and a wrong password
And I click login three times
Then the login page should show message saying user lock out