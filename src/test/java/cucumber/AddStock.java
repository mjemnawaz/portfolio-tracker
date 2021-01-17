package cucumber;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddStock {
	
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	
	public AddStock() {
		driver = Init.getDriver();
	}
	
	
	//-----------------------------Scenario 1: Empty number of shares--------------------
	
	@Given("I am logged in on the home page")
	public void i_am_logged_in_on_the_home_page() {
	    
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	    // Write code here that turns the phrase above into concrete actions
		driver.get(ROOT_URL);
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		// enter in tim for username
		WebElement userLogin = driver.findElement(By.id("username-login"));
		userLogin.sendKeys("tim");
		
		
		// password
		WebElement passwordField = driver.findElement(By.id("password-login"));
		passwordField.sendKeys("123");
		
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

	@When("I click on the Add Stock dashboard option")
	public void i_click_on_the_Add_Stock_dashboard_option() {
	    
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

	@When("I enter {string} in the ticker field")
	public void i_enter_in_the_ticker_field(String string) {
	    
		WebElement tickerField = driver.findElement(By.id("addStockTicker"));
		tickerField.sendKeys(string);
		
	}

	@When("I click {string}")
	public void i_click(String string) {
	    
		WebElement addStockBtn = driver.findElement(By.id("addStockButton"));
		addStockBtn.click();
		
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("I should see the error {string} in the modal")
	public void i_should_see_the_error_in_the_modal(String string) {
	    
		WebElement errorMess = driver.findElement(By.id("addStockError"));
		
		String message = errorMess.getText();
		
		String expectedMessage = string;
		
		assertEquals("problem checking empty number of shares", message, expectedMessage);
		
	}
	
	
	
	// ----------------------- Scenario 2: Failed to add stock due to 0 shares ----------------------------------------
	@When("I enter {string} for the number of shares")
	public void i_enter_for_the_number_of_shares(String string) {
		WebElement numShares = driver.findElement(By.id("numShares"));
		numShares.sendKeys(string);
	}
	
	
	
	// Scenario 3: Failed to add stock due to entering an invalid ticker symbol
	
	
	// Scenario 4: Added a stock successfully to portfolio
	
	@Then("I should see a success message in the modal")
	public void i_should_see_a_success_message_in_the_modal() {
		
		try {
			Thread.sleep(10000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
		WebElement successMess = driver.findElement(By.id("addStockSuccess"));
		
		String message = successMess.getText();
		
		String expectedMessage = "Success";
		
		assertEquals("problem valid stock addition", message, expectedMessage);
	}
	
	
}
