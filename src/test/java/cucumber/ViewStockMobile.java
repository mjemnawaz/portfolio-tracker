package cucumber;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewStockMobile {
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	
	public ViewStockMobile() {
		driver = Init.getMobileDriver();
	}
	
	@When("I click on View Stock on my dashboard on mobile")
	public void i_click_on_View_Stock_on_my_dashboard_on_mobile() {
	   
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement viewModal = driver.findElement(By.id("historyCard"));
		
		viewModal.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@When("I enter in {string} for the ticker symbol on mobile")
	public void i_enter_in_for_the_ticker_symbol_on_mobile(String string) {
	    
		WebElement tickerSearchBox = driver.findElement(By.id("stockHistory"));
		
		tickerSearchBox.sendKeys(string);
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@When("I click the View Stock button on mobile")
	public void i_click_the_View_Stock_button_on_mobile() {
	    
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
		
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", cancelButton);
	}
	
	@Then("I should see {string} in the View Stocks table of the dashboard on mobile")
	public void i_should_see_in_the_View_Stocks_table_of_the_dashboard_on_mobile(String string) {
		
	    
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
	
	@When("I click on the delete button in the row for GE on mobile")
	public void i_click_on_the_delete_button_in_the_row_for_GE_on_mobile() {
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("HERE BEFORE");
	    
		WebElement deleteButton = driver.findElement(By.id("removeHistoryButton1"));
		
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", deleteButton);
		
		System.out.println("HERE AFTER");
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@When("I click Delete Stock in the pop-up modal on mobile")
	public void i_click_Delete_Stock_in_the_pop_up_modal_on_mobile() {
	    
		WebElement deleteStockBtn = driver.findElement(By.id("confirmRemoval"));
		
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", deleteStockBtn);
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Then("{string} should no longer be in the View Stocks table on mobile")
	public void should_no_longer_be_in_the_View_Stocks_table_on_mobile(String string) {
	    
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
