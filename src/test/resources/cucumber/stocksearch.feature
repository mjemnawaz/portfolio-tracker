Feature: Stock Search

Scenario: Successful Stock Search
Given that I am on the search bar
And I type in a valid stock symbol either lowercase or uppercase
When I hit the search bar
Then I should see the information of my stock

Scenario: Uncussessful Stock Search
Given that I am on the search bar
And I type in an invalid stock symbol either uppercase or lowercase
When I hit the search bar
Then I stay on the search page with an error message saying that there is no stock that matches the names