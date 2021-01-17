Feature: password hash

Scenario: Ensuring password is hashed after register
Given I am on the registration page
When I input user information with an username and password
And I click create user button
Then the password in the database registered should not match the actual input password