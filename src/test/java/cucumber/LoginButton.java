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

public class LoginButton {
	
	private final WebDriver driver;
	
	public LoginButton() {
		driver = Init.getDriver();
	}
	
	@Given("I am on the user login page")
	public void I_am_on_the_user_login_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080/index.jsp");
	}

	@Then("I should see button with text Login")
	public void I_should_see_button_with_text_Login() {
	    // Write code here that turns the phrase above into concrete actions
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		String loginMessage = loginButton.getText();
		assertEquals(loginMessage, "Login");
	}
	
	
}
