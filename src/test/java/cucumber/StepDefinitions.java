package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
	private static final String ROOT_URL = "http://localhost:8080/";

	private final WebDriver driver = new ChromeDriver();
	
	/* ----------------------- Register.feature ---------------------------------- */
	
	
	
	
	/* ----------------------- Scenario: Unsuccessful registration due to empty input fields ---------------------------------- */
	
	
	@Given("I am on the register page")
	public void i_am_on_the_register_page() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	    // Write code here that turns the phrase above into concrete actions
		driver.get(ROOT_URL + "register.jsp");
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@When("I enter {string} for username")
	public void i_enter_for_username(String string) {
	    
		WebElement queryBox = driver.findElement(By.id("username-register"));
		queryBox.sendKeys(string);
	    
	}

	@When("I press Create User")
	public void i_press_Create_User() {
	    
		WebElement createButton = driver.findElement(By.id("registerButton"));
		
		createButton.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should see {string} at the top of the form")
	public void i_should_see_at_the_top_of_the_form(String string) {
	    
		String expectedError = "Inputs cannot be empty";
		
		WebElement actualError = driver.findElement(By.id("registerError"));
		
		String actualOutput = actualError.getText();
		
		assertEquals("blank registration fields failing", expectedError, actualOutput);
		
	}
	
	/* ----------------------- Scenario: Unsuccessful registration due to password fields not matching ---------------------------------- */
	
	@Given("I am at the register page")
	public void i_am_at_the_register_page() {
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	    
		driver.get(ROOT_URL + "register.jsp");
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@When("I type {string} for username")
	public void i_type_for_username(String string) {
		WebElement queryBox = driver.findElement(By.id("username-register"));
		queryBox.sendKeys(string);
		
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@When("I type {string} for password")
	public void i_type_for_password(String string) {
	   
		WebElement queryBox = driver.findElement(By.id("password-register"));
		queryBox.sendKeys(string);
		
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@When("I type {string} for confirm password")
	public void i_type_for_confirm_password(String string) {
		WebElement queryBox = driver.findElement(By.id("password-register-confirm"));
		queryBox.sendKeys(string);
		
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should see the error {string} at the top of the form")
	public void i_should_see_the_error_at_the_top_of_the_form(String string) {
	    

		String expectedError = string;
		
		WebElement actualError = driver.findElement(By.id("registerError"));
		
		String actualOutput = actualError.getText();
		
		assertEquals("problem with mismatching password fields for registration", expectedError, actualOutput);
		
	}
	
	
	
	/* ----------------------- Scenario: User cancels registration ---------------------------------- */
	
	
	
	@When("I hit the Cancel button")
	public void i_hit_the_Cancel_button() {
	    
		WebElement cancelButton = driver.findElement(By.id("cancelRegistration"));
		
		cancelButton.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("I should be taken to the Login page")
	public void i_should_be_taken_to_the_Login_page() {
		
		String expectedFormTitle = "Login";
	    
		WebElement loginTitle = driver.findElement(By.id("loginTitle"));
		String actualFormTitle = loginTitle.getText();
		
		assertEquals("error redirecting to login after user cancels registration", expectedFormTitle, actualFormTitle);
		
	}
	
	/* ----------------------- Scenario: Successful registration ---------------------------------- */
	
			// No additional step definitions required because reuses those previously defined
	
	/* ----------------------- Unsuccessful registration due to creating an account with existing credentials ---------------------------------- */
	
			// No additional step definitions required
	
	
	
	/* ----------------------- Login.feature ---------------------------------- */
	
	
	/* ----------------------- Scenario: Unsuccessful login due to empty input fields ---------------------------------- */
	
	@Given("I am currently on the Login Page")
	public void i_am_currently_on_the_Login_Page() {
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
	}

	@When("I enter {string} in the username field")
	public void i_enter_in_the_username_field(String string) {
	    
		WebElement userLogin = driver.findElement(By.id("username-login"));
		userLogin.sendKeys(string);
		
	}

	@When("I press the Login button")
	public void i_press_the_Login_button() {
	    
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("I should see an error {string} on the form")
	public void i_should_see_an_error_on_the_form(String string) {
	   
		String expectedError = string;
		
		WebElement actualError = driver.findElement(By.id("loginError"));
		
		String actualOutput = actualError.getText();
		
		assertEquals("problem with empty login fields", expectedError, actualOutput);
		
	}
	
	/* ----------------------- Scenario: Unsuccessful login due to invalid username and/or password ---------------------------------- */
	
	@When("I put {string} in the password field")
	public void i_put_in_the_password_field(String string) {
	    
		WebElement passwordField = driver.findElement(By.id("password-login"));
		passwordField.sendKeys(string);
	}
	
	/* ----------------------- Scenario: Successful login ---------------------------------- */
	
	@Then("I should be taken to the home page")
	public void i_should_be_taken_to_the_home_page() {
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	    
		WebElement homePageLink = driver.findElement(By.id("logoutLink"));
		
		// know that on homepage if log out link visible
		
		String linkText = homePageLink.getText();
		
		assertEquals("Problem with valid login", "Logout", linkText);
		
	}
	
	

	@After()
	public void cleanup() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}
}
