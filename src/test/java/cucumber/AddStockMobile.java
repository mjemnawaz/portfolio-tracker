package cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddStockMobile {
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	
	public AddStockMobile() {
		driver = Init.getMobileDriver();
	}
	
	// Mobile test
	@Given("I am logged in on the home page on mobile device")
	public void i_am_logged_in_on_the_home_page_on_mobile_device() {
	    
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	    // Write code here that turns the phrase above into concrete actions
		driver.get(ROOT_URL);
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		// enter in tim for username
		WebElement userLogin = driver.findElement(By.id("username-login"));
		userLogin.sendKeys("register");
		
		
		// password
		WebElement passwordField = driver.findElement(By.id("password-login"));
		passwordField.sendKeys("register");
		
		// click login button
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
		// should now be on home page
		
	}
	
	@When("I click on the Add Stock dashboard option on mobile")
	public void i_click_on_the_Add_Stock_dashboard_option_on_mobile() {
	    
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement addButton = driver.findElement(By.id("addCard"));
		addButton.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@When("I enter {string} in the ticker field on mobile")
	public void i_enter_in_the_ticker_field_on_mobile(String string) {
	    
		WebElement tickerField = driver.findElement(By.id("addStockTicker"));
		tickerField.sendKeys(string);
		
	}
	
	@When("I enter {string} for the number of shares on mobile")
	public void i_enter_for_the_number_of_shares_on_mobile(String string) {
		WebElement numShares = driver.findElement(By.id("numShares"));
		numShares.sendKeys(string);
	}
	
	@When("I enter {string} for date purchased on mobile")
	public void i_enter_for_date_purchased_on_mobile(String string) {
	    
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
	
	@When("I click the {string} button in the modal on mobile")
	public void i_click_the_button_in_the_modal_on_mobile(String string) {
	    
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
	
	@Then("I should see {string} in the My Stocks table below on mobile")
	public void i_should_see_in_the_My_Stocks_table_below_on_mobile(String string) {
	   
		   
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
}
