package cucumber;

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

public class PasswordHash {
	
	private final WebDriver driver;
	
	public PasswordHash() {
		driver = Init.getDriver();
	}
	
	@Given("I am on the registration page")
	public void i_am_on_the_registration_page() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("http://localhost:8080/register.jsp");
	}

	@When("I input user information with an username and password")
	public void when_I_input_user_information_with_an_username_and_password() {
	    // Write code here that turns the phrase above into concrete actions
		WebElement usernameInput = driver.findElement(By.xpath("/html/body/form/div[2]/div/div/input"));
		usernameInput.sendKeys("testHash");
		WebElement passwordInput = driver.findElement(By.xpath("/html/body/form/div[3]/div/div/input"));
		passwordInput.sendKeys("testHash");
		WebElement confirmInput = driver.findElement(By.xpath("/html/body/form/div[4]/div/div/input"));
		confirmInput.sendKeys("testHash");
	}

	@When("I click create user button")
	public void i_click_create_user_button() {
		WebElement registerButton = driver.findElement(By.xpath("/html/body/form/div[5]/button"));
	    registerButton.click();
	}

	@Then("the password in the database registered should not match the actual input password")
	public void the_password_in_the_database_registered_should_not_match_the_actual_input_password () throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/form/div[4]/button")));
		SQLiteDB db = new SQLiteDB();
		String sql = "SELECT pk FROM user WHERE username = ?";
		PreparedStatement ps = db.getConn().prepareStatement(sql);
		ps.setString(1,"testHash");
		ResultSet rs = ps.executeQuery();
		String password = "";
		while (rs.next()) {
			password = rs.getString("pk");
		}
		//System.out.println("password: " + password);
		db.closeConn();
		assertTrue(!password.equals("testHash"));
		db.removeUser("testHash");
	}
	
	
}
