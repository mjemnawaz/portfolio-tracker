package cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class Logout {
	
	private final WebDriver driver;
	
	public Logout() {
		driver = Init.getDriver();
	}
	

	@When("I sign up and sign in with a user")
	public void I_sign_up_and_sign_in_with_a_user() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		WebElement usernameInput = driver.findElement(By.xpath("/html/body/form/div[2]/div/div/input"));
		usernameInput.sendKeys("logout");
		WebElement passcodeInput = driver.findElement(By.xpath("/html/body/form/div[3]/div/div/input"));
		passcodeInput.sendKeys("logout");
		WebElement confirmInput = driver.findElement(By.xpath("/html/body/form/div[4]/div/div/input"));
		confirmInput.sendKeys("logout");
		WebElement registerButton = driver.findElement(By.xpath("/html/body/form/div[5]/button"));
	    registerButton.click();
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		WebElement userLogin = driver.findElement(By.id("username-login"));
		userLogin.sendKeys("logout");
		WebElement passwordInput = driver.findElement(By.id("password-login"));
		passwordInput.sendKeys("logout");
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();
	}

	@When("I click logout")
	public void I_click_logout() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		WebElement logoutButton = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/h2/a"));
	    logoutButton.click();
	}

	@Then("I should be back to login page")
	public void I_should_be_back_to_login_page () throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		String loginMessage = loginButton.getText();
		assertEquals(loginMessage, "Login");
		SQLiteDB db = new SQLiteDB();
		db.removeUser("logout");
	}
	
	
}
