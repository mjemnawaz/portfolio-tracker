
Feature: User account creation/registration
	
  Scenario: Unsuccessful registration due to empty input fields
    Given I am on the register page
    When I enter "tommy" for username
    And I press Create User
    Then I should see "Inputs cannot be empty" at the top of the form
    
  Scenario: Unsuccessful registration due to password fields not matching
    Given I am at the register page
    When I type "tommy" for username
    And I type "123" for password
    And I type "345" for confirm password
    And I press Create User
    Then I should see the error "Password and Confirm Password must match" at the top of the form
    
  Scenario: User cancels registration
    Given I am at the register page
    When I type "tommy" for username
    And I hit the Cancel button
    Then I should be taken to the Login page
    
  Scenario: Successful registration
    Given I am at the register page
    When I type "tommy" for username
    And I type "123" for password
    And I type "123" for confirm password
    And I press Create User
    Then I should be taken to the Login page
    
   Scenario: Unsuccessful registration due to creating an account with existing credentials
    Given I am at the register page
    When I type "tommy" for username
    And I type "123" for password
    And I type "123" for confirm password
    And I press Create User
    Then I should see the error "User already exists" at the top of the form
    
  
    
    
    