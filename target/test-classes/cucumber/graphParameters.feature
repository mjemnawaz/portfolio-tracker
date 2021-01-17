Feature: Checking Graph Parameters

	Scenario: Failed to view stock due to date going over a year
		Given I am logged in on the home page
	    When I click on View Stock on my dashboard
	    When I enter in "AMZN" for the ticker symbol
	    When I click the View Stock button
	    When I set the graph date as more than a year ago
		And I click the check box for "AMZN" under the Stock History table
		Then I should see the graph error "Cannot go back more than a year"
	
	Scenario: Failed to view stock due to end date being before start date
		Given I am logged in on the home page
	    When I click on View Stock on my dashboard
	    When I enter in "AMZN" for the ticker symbol
	    When I click the View Stock button
	    When I set the end date before the start date
	    And I click the check box for "AMZN" under the Stock History table
		Then I should see the graph error "End Date cannot be before Start Date"
	
	Scenario: empty end date
		Given I am logged in on the home page
	    When I click on View Stock on my dashboard
	    When I enter in "AMZN" for the ticker symbol
	    When I click the View Stock button
	    When I have an empty field for the end date
	    And I click the check box for "AMZN" under the Stock History table
		Then my end date should be set to the current date