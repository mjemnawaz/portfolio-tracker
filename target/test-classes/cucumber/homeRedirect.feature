Feature: homeRedirect

Scenario: Ensuring redirection to login page when user access home page without login
Given I am on the home page without logging in
Then I should be redirected back to login page