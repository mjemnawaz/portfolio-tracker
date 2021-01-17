Feature: Adding a stock to the portfolio on mobile device

Scenario: Success adding NASDAQ stock AMZN to My Stocks table with date purchased a year ago on mobile device
Given I am logged in on the home page on mobile device
When I click on the Add Stock dashboard option on mobile
And I enter "AMZN" in the ticker field on mobile
And I enter "1" for the number of shares on mobile
And I enter "11072020" for date purchased on mobile
And I click the "Add Stock" button in the modal on mobile
Then I should see "AMZN" in the My Stocks table below on mobile