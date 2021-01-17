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

public class Lockout {
	
	private final WebDriver driver;
	
	public Lockout() {
		driver = Init.getDriver();
	}
	
	@Given("I am on the login page")
	public void i_am_on_the_login_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080");
	}

	@When("I input user information with an username and a wrong password")
	public void when_I_input_user_information_with_an_username_and_a_wrong_password() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		SQLiteDB db = new SQLiteDB();
		db.addUser("lock", "lock");
		WebElement usernameInput = driver.findElement(By.xpath("/html/body/form/div[2]/div/div/input"));
		usernameInput.sendKeys("lock");
		WebElement passwordInput = driver.findElement(By.xpath("/html/body/form/div[3]/div/div/input"));
		passwordInput.sendKeys("loc");
	}

	@When("I click login three times")
	public void i_click_login_three_times() {
		WebElement loginButton = driver.findElement(By.xpath("/html/body/form/div[4]/button"));
		loginButton.click();
		loginButton.click();
		loginButton.click();
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("the login page should show message saying user lock out")
	public void the_login_page_should_show_message_saying_user_lock_out () throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		WebElement errorBox = driver.findElement(By.id("loginError"));
		String errorMessage = errorBox.getText();
		assertEquals(errorMessage, "User locked out, try again in 1 minute");
		SQLiteDB db = new SQLiteDB();
		db.removeUser("lock");
	}
	
}
