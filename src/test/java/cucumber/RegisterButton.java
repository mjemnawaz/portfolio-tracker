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

public class RegisterButton {
	
	private final WebDriver driver;
	
	public RegisterButton() {
		driver = Init.getDriver();
	}
	
	@Given("I am on the user registration page")
	public void I_am_on_the_user_registration_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080/register.jsp");
	}

	@Then("I should see buttons with texts Cancel and Create User")
	public void I_should_see_buttons_with_texts_Cancel_and_Create_User () {
	    // Write code here that turns the phrase above into concrete actions
		WebElement registerButton = driver.findElement(By.id("registerButton"));
		String registerMessage = registerButton.getText();
		assertEquals(registerMessage, "Create User");
		WebElement cancelButton = driver.findElement(By.id("cancelRegistration"));
		String cancelMessage = cancelButton.getText();
		assertEquals(cancelMessage, "Cancel");
	}
	
	
}
