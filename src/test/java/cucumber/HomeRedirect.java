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

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomeRedirect {
	
	private final WebDriver driver;
	
	public HomeRedirect() {
		driver = Init.getDriver();
	}
	
	@Given("I am on the home page without logging in")
	public void I_am_on_the_home_page_without_logging_in() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080/home.jsp");
		try {
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should be redirected back to login page")
	public void I_should_be_redirected_back_to_login_page() {
	    // Write code here that turns the phrase above into concrete actions
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		String loginMessage = loginButton.getText();
		assertEquals(loginMessage, "Login");
	}
	
	
}
