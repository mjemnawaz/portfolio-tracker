Feature: Password hidden
    
   Scenario: Password hidden on register page
    Given I am at the moment on register
    When I enter "tommy" to create a username
    And I put "123" in the first password field
    And I also type "123" in the confirm password field
   	Then I should not see "123" in either fields
   	
   	
   	
   Scenario: Password hidden on login page
    Given I am at the moment on the login page
    When I enter "tommy" for my specific username
    And I put "123" in the required password field
   	Then I should not see "123" in the password field