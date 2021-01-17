package cucumber;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import csci310.SQLiteDB;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HidePassword {
	
	private static final String ROOT_URL = "http://localhost:8080/";
	
	private final WebDriver driver;
	
	public HidePassword() {
		driver = Init.getDriver();
	}
	
	
	// ----------------- Scenario 1: Password hidden on register page -------------------------------

	@Given("I am at the moment on register")
	public void i_am_at_the_moment_on_register() {
	    driver.get(ROOT_URL + "register.jsp");
	}
	
	@When("I enter {string} to create a username")
	public void i_enter_to_create_a_username(String string) {
		WebElement queryBox = driver.findElement(By.id("username-register"));
		queryBox.sendKeys(string);
	}
	
	@When("I put {string} in the first password field")
	public void i_put_in_the_first_password_field(String string) {
	   
		WebElement queryBox = driver.findElement(By.id("password-register"));
		queryBox.sendKeys(string);
		
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@When("I also type {string} in the confirm password field")
	public void i_also_type_in_the_confirm_password_field(String string) {
	    
		WebElement queryBox = driver.findElement(By.id("password-register-confirm"));
		queryBox.sendKeys(string);
		
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	@Then("I should not see {string} in either fields")
	public void i_should_not_see_in_either_fields(String string) {
	    
		WebElement queryBox = driver.findElement(By.id("password-register"));
		
		WebElement confirmPass = driver.findElement(By.id("password-register-confirm"));
		
		String passType = queryBox.getAttribute("type");
		
		String confirmPassType = confirmPass.getAttribute("type");
		
		String expectedType = "password";
		
		assertTrue("problem with checking password hidden on register",passType.equals(expectedType) && confirmPassType.equals(expectedType));
		
	}
	
	
	
	// --------------------------------Scenario 2: password hidden on login page---------------------------------
	
	@Given("I am at the moment on the login page")
	public void i_am_at_the_moment_on_the_login_page() {
	    driver.get(ROOT_URL);
	}

	@When("I enter {string} for my specific username")
	public void i_enter_for_my_specific_username(String string) {
		WebElement userLogin = driver.findElement(By.id("username-login"));
		userLogin.sendKeys(string);
	}

	@When("I put {string} in the required password field")
	public void i_put_in_the_required_password_field(String string) {
		WebElement passwordField = driver.findElement(By.id("password-login"));
		passwordField.sendKeys(string);
	}

	@Then("I should not see {string} in the password field")
	public void i_should_not_see_in_the_password_field(String string) {
	    
		WebElement passBox = driver.findElement(By.id("password-login"));
		
		String actualPassType = passBox.getAttribute("type");
		
		String expectedType = "password";
		
		assertEquals("problem with hidden password on login page", actualPassType, expectedType);
		
	}

	
	
}