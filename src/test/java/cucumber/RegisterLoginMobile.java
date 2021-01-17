package cucumber;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import csci310.SQLiteDB;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

//registerMobile should be ran before loginMobile
public class RegisterLoginMobile {
	private final WebDriver driver;
	
	public RegisterLoginMobile() {
		driver = Init.getMobileDriver();
	}
	
	@Given("I am on the register page on mobile device")
	public void I_am_on_the_register_page_on_mobile_device() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080/register.jsp");
	}
	
	@When("I input user information with an username and matching passwords")
	public void I_input_user_information_with_an_username_and_matching_passwords() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		WebElement usernameInput = driver.findElement(By.xpath("/html/body/form/div[2]/div/div/input"));
		usernameInput.sendKeys("register");
		WebElement passcodeInput = driver.findElement(By.xpath("/html/body/form/div[3]/div/div/input"));
		passcodeInput.sendKeys("register");
		WebElement confirmInput = driver.findElement(By.xpath("/html/body/form/div[4]/div/div/input"));
		confirmInput.sendKeys("register");
	}
	
	@When("I click the register button on the device")
	public void I_click_the_register_button_on_the_device () {
	    // Write code here that turns the phrase above into concrete actions
		WebElement registerButton = driver.findElement(By.xpath("/html/body/form/div[5]/button"));
	    registerButton.click();
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should be taken to login page on the device")
	public void I_should_be_taken_to_login_page_on_the_device () throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		WebElement loginButton = driver.findElement(By.id("loginButton"));
		String loginMessage = loginButton.getText();
		assertEquals(loginMessage, "Login");
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Given("I am on the login page on mobile device")
	public void I_am_on_the_login_page_on_mobile_device() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080/index.jsp");
	}
	
	@When("I input user information with correct username and password")
	public void I_input_user_information_with_correct_username_and_password() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		WebElement usernameInput = driver.findElement(By.xpath("/html/body/form/div[2]/div/div/input"));
		usernameInput.sendKeys("register");
		WebElement passwordInput = driver.findElement(By.xpath("/html/body/form/div[3]/div/div/input"));
		passwordInput.sendKeys("register");
	}
	
	@When("I click the login button on the device")
	public void I_click_the_login_button_on_the_device () {
	    // Write code here that turns the phrase above into concrete actions
		WebElement loginButton = driver.findElement(By.xpath("/html/body/form/div[4]/button"));
		loginButton.click();
	}

	@Then("I should be taken to home page on the device")
	public void I_should_be_taken_to_home_page_on_the_device () throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		try {
			Thread.sleep(3000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	    
		WebElement homePageButton = driver.findElement(By.id("logoutLink"));
		String homePageMessage = homePageButton.getText();
		assertEquals(homePageMessage, "Logout");
//		SQLiteDB db = new SQLiteDB();
//		db.removeUser("register");
	}
	
	
}
