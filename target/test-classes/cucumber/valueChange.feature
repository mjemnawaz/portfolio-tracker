Feature: Checking Portfolio Value Change magnitude, color, and arrow dir
          	
Scenario: Adding Google should result in pos percent change with color green and up arrow
Given I am logged in on the home page
When I click on the Add Stock dashboard option
And I enter "GOOGL" in the ticker field
And I enter "1" for the number of shares
And I enter today for the day purchased
And I click the "Add Stock" button in the modal
Then I should see a percent change in "rgba(0, 128, 0, 1)" with an "up" arrow
   	
Scenario: Adding Google and selling today should result in neg percent change with color red and down arrow
Given I am logged in on the home page
When I click on the Add Stock dashboard option
And I enter "GOOGL" in the ticker field
And I enter "5" for the number of shares
And I enter "11082020" for date purchased
And I enter today for the day sold
And I click the "Add Stock" button in the modal
Then I should see a percent change in "rgba(255, 0, 0, 1)" with an "down" arrow
   	
   	