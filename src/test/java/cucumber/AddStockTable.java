package cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddStockTable {
	
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	
	public AddStockTable() {
		driver = Init.getDriver();
	}
	
	
	// ------------------------------- Scenario 1: Error adding stock to portfolio due to empty date purchased field
	
	@When("I click the {string} button below")
	public void i_click_the_button_below(String string) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement addBtn = driver.findElement(By.id("addStockButton"));
		addBtn.click();
		
	}
	
	@Then("I should see an error message in the modal {string}")
	public void i_should_see_an_error_message_in_the_modal(String string) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		String errorMess = driver.findElement(By.id("addStockError")).getText();
		
		assertEquals("problem with error message when empty date purchased", errorMess, string);
		
	}
	
	// ------------------------------- Scenario 2: Error adding stock to portfolio due to date sold earlier than date purchased
	
	@When("I enter {string} for date purchased")
	public void i_enter_for_date_purchased(String string) {
	    
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement datePurchased = driver.findElement(By.id("datePurchased"));
		datePurchased.sendKeys(string);
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@When("I enter {string} for date sold")
	public void i_enter_for_date_sold(String string) {
	    
	    
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement dateSold = driver.findElement(By.id("dateSold"));
		dateSold.sendKeys(string);
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// -------------------- Scenario 3: Success adding NASDAQ stock AMZN to My Stocks table with date purchased a year ago
	
	@When("I click the {string} button in the modal")
	public void i_click_the_button_in_the_modal(String string) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement addBtn = driver.findElement(By.id("addStockButton"));
		addBtn.click();
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		   
//		try {
//			Thread.sleep(90000);
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}

	@Then("I should see {string} in the My Stocks table below")
	public void i_should_see_in_the_My_Stocks_table_below(String string) {
	   
		   
	    boolean isFound = false;
	    List<WebElement> rows = driver.findElements(By.cssSelector(".myStock"));
	    
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
	    
	    assertTrue("problem verifying " + string + " in My Stocks table", isFound == true);
		
	}
	

	// Scenario 4: Success adding NYSE stock GE to My Stocks table with date purchased a year ago and sold yesterday
	
	
	// Scenario 5: Success removing AAPL from My Stocks table
	
	@When("I select the Delete button in the AAPL row of My Stocks")
	public void i_select_the_Delete_button_in_the_AAPL_row_of_My_Stocks() {
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement deleteBtn = driver.findElement(By.id("deleteButton1"));
		
		deleteBtn.click();
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	    
	}

	@When("I press Delete Stock inside the Delete Modal")
	public void i_press_Delete_Stock_inside_the_Delete_Modal() {
		
		WebElement deleteBtn = driver.findElement(By.id("confirmDelete"));
		deleteBtn.click();
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		// click the cancel modal button
		WebElement cancelBtn = driver.findElement(By.id("cancelDelete"));
		cancelBtn.click();
		
	    
	}

	@Then("I should not see {string} in the My Stocks table anymore")
	public void i_should_not_see_in_the_My_Stocks_table_anymore(String string) {
		
		  boolean isFound = false;
		    List<WebElement> rows = driver.findElements(By.cssSelector(".myStock"));
		    
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
		    
		    assertTrue("problem verifying AAPL removed from graph", isFound == false);
	    
	}
	
	
	

}
