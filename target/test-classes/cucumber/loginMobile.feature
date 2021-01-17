Feature: loginMobile

Scenario: Ensuring login works on mobile device
Given I am on the login page on mobile device
When I input user information with correct username and password
And I click the login button on the device
Then I should be taken to home page on the device