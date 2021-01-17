Feature: User login
	
  Scenario: Unsuccessful login due to empty input fields
    Given I am currently on the Login Page
    When I enter "tommy" in the username field
    And I press the Login button
    Then I should see an error "Inputs cannot be empty" on the form
    
  Scenario: Unsuccessful login due to username/password not being registered in the database
    Given I am currently on the Login Page
    When I enter "bob" in the username field
    And I put "123" in the password field
    And I press the Login button
    Then I should see an error "Invalid Credentials" on the form
    
   Scenario: Unsuccessful login due to invalid username/password for registered user
    Given I am currently on the Login Page
    When I enter "tommy" in the username field
    And I put "test" in the password field
    And I press the Login button
    Then I should see an error "Invalid Credentials" on the form
    
   Scenario: Successful login
    Given I am currently on the Login Page
    When I enter "tommy" in the username field
    And I put "123" in the password field
    And I press the Login button
    Then I should be taken to the home page    