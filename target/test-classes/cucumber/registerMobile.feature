Feature: registerMobile

Scenario: Ensuring register works on mobile device
Given I am on the register page on mobile device
When I input user information with an username and matching passwords
And I click the register button on the device
Then I should be taken to login page on the device