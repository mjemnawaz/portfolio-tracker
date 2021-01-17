package cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewStockTable {
	
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	
	public ViewStockTable() {
		driver = Init.getDriver();
	}
	
	
	// -------------- Scenario 1: Error adding stock to View Stock table due to empty historical ticker symbol
	
	@When("I click the View Stock button")
	public void i_click_the_View_Stock_button() {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement viewButton = driver.findElement(By.id("viewStockButton"));
		
		viewButton.click();
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement cancelButton = driver.findElement(By.id("viewCancel"));
		
		cancelButton.click();
		
		
	}

	@Then("I should see the error message above the modal {string}")
	public void i_should_see_the_error_message_above_the_modal(String string) {
	    
		// check error message
		String errorMess = driver.findElement(By.id("viewStockError")).getText();
		
		assertEquals("problem checking empty ticker name message", string, errorMess);
		
	}
	
	//-------------------- Scenario 2: Error adding stock to View Stock table due to invalid ticker symbol
	
	@When("I enter in {string} for the ticker symbol")
	public void i_enter_in_for_the_ticker_symbol(String string) {
	    
		WebElement tickerSearchBox = driver.findElement(By.id("stockHistory"));
		
		tickerSearchBox.sendKeys(string);
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	// ------------- Scenario 3: Success adding stock from NASDAQ to View Stocks table
	
	@Then("I should see {string} in the View Stocks table of the dashboard")
	public void i_should_see_in_the_View_Stocks_table_of_the_dashboard(String string) {
		
	    
	    boolean isFound = false;
	    List<WebElement> rows = driver.findElements(By.cssSelector(".historyRow"));
	    
	    List<WebElement> columnsList = null;
	    
	    for(WebElement row : rows){
	    	
	    	columnsList = row.findElements(By.tagName("td"));
	    	
	    	 for (WebElement column : columnsList) {
	    		 
	    		 if (column.getAttribute("innerHTML").equals(string)) {
	    			 
	    			 isFound = true;
	    			 break;
	    			 
	    		 }
                 
	    		 
	    	 }
	    	 
	    	 if (isFound) {
	    		 break;
	    	 }
	    	
	    }
	    
	    assertTrue("problem verifying AMZN in View Stocks table", isFound == true);
	    
	    
		
	}
	
	// ------------------------Scenario 4: Success adding stock from NYSE to View Stocks table
	
	// -------------------------Scenario 5: Success removing NYSE stock from View Stocks table
	
	@When("I click on the delete button in the row for GE")
	public void i_click_on_the_delete_button_in_the_row_for_GE() {
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("HERE BEFORE");
	    
		WebElement deleteButton = driver.findElement(By.id("removeHistoryButton1"));
		
		deleteButton.click();
		
		System.out.println("HERE AFTER");
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@When("I click Delete Stock in the pop-up modal")
	public void i_click_Delete_Stock_in_the_pop_up_modal() {
	    
		WebElement deleteStockBtn = driver.findElement(By.id("confirmRemoval"));
		
		deleteStockBtn.click();
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("{string} should no longer be in the View Stocks table")
	public void should_no_longer_be_in_the_View_Stocks_table(String string) {
	    
		   boolean isFound = false;
		    List<WebElement> rows = driver.findElements(By.cssSelector(".historyRow"));
		    
		    List<WebElement> columnsList = null;
		    
		    for(WebElement row : rows){
		    	
		    	columnsList = row.findElements(By.tagName("td"));
		    	
		    	 for (WebElement column : columnsList) {
		    		 
		    		 if (column.getAttribute("innerHTML").equals(string)) {
		    			 
		    			 isFound = true;
		    			 break;
		    			 
		    		 }
	                 
		    		 
		    	 }
		    	 
		    	 if (isFound) {
		    		 break;
		    	 }
		    	
		    }
		    
		    assertTrue("problem verifying GE removed from graph", isFound == false);
		
		
	}

}
