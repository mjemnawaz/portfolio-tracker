Feature: Graph Functionality

Scenario: Check the default graph
Given I am logged in and I am on the home page with username "randouser" and password "randopass"
Then I should see the default 3 month portfolio graph

Scenario: Graph the S&P Line
Given I am logged in and I am on the home page with username "randouser" and password "randopass"
When I click on the S&P Checkbox
Then I should see the S&P graph

Scenario: Graph starts with earliest transaction
Given I am logged in and I am on the home page with username "randouser" and password "randopass"
When I click on the S&P Checkbox
Then I should see the portfolio graph with the correct range

Scenario: Graph different subsets of the portfolio
Given I am logged in and I am on the home page with username "randouser" and password "randopass"
When I click on one stock to include in the graph
And I click on another stock to include in the graph
Then I should see the portfolio graph update

Scenario: Change increments on graph
Given I am logged in and I am on the home page with username "randouser" and password "randopass"
When I click on one stock to include in the graph
And I change the increments on the graph from daily to weekly
Then the intervals should go from 1 to 7
