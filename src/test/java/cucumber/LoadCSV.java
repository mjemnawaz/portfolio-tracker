package cucumber;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoadCSV {
	
	private final WebDriver driver;

	private String username = "akjgherbklrehb2t", password = "wobehqpeopqh9";
	
	
	public LoadCSV() {
		driver = Init.getDriver();
	}
	
	
	@Given("I am logged in and I am on the home page")
	public void i_am_logged_in_and_I_am_on_the_home_page() {
		driver.get("http://localhost:8080/register.jsp");
		WebElement usernameRegister = driver.findElement(By.cssSelector("#username-register"));
		usernameRegister.sendKeys(username);
		WebElement passwordRegister = driver.findElement(By.cssSelector("#password-register"));
		passwordRegister.sendKeys(password);
		WebElement confirmRegister = driver.findElement(By.cssSelector("#password-register-confirm"));
		confirmRegister.sendKeys(password);
		WebElement register = driver.findElement(By.cssSelector("#registerButton"));
	    register.click();
	    driver.get("http://localhost:8080/index.jsp");
	    WebElement usernameLogin = driver.findElement(By.cssSelector("#username-login"));
	    usernameLogin.sendKeys(username);
	    WebElement passwordLogin = driver.findElement(By.cssSelector("#password-login"));
	    passwordLogin.sendKeys(password);
	    WebElement login = driver.findElement(By.cssSelector("#loginButton"));
	    login.click();
	}

	@When("I click the button to input CSV data")
	public void i_click_the_button_to_input_CSV_data() {
		if (driver.findElements(By.cssSelector("#portfolioCard")).size() == 0) System.out.println("Doesn't exist!");
		else System.out.println("Portfolio card exists! " + driver.findElements(By.cssSelector("#portfolioCard")).size());
		new Actions(driver).moveToElement(driver.findElement(By.cssSelector("#portfolioCard"))).click().build().perform();
		System.out.println("Clicked.");
	}

	// SCENARIO ONE - VALID FILE, VALID DATA
	
	@When("I provide a valid CSV file")
	public void i_provide_a_valid_CSV_file() {
		File file = new File("files/input.csv");
		String path = file.getAbsolutePath();
		WebDriverWait waiter = new WebDriverWait(driver, 5000);
		waiter.until( ExpectedConditions.presenceOfElementLocated(By.cssSelector("#csvFile")) );
		WebElement chooseFile = driver.findElement(By.cssSelector("#csvFile"));
	    chooseFile.sendKeys(path);
	    System.out.println("Sent: " + file.getAbsolutePath());
	    waiter.until( ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='csvForm']/button")) );
	    WebElement button = driver.findElement(By.cssSelector("#portfolioButton"));
 		if (driver.findElements(By.cssSelector("#portfolioButton")).size()==0) 
	    	System.out.println("Can't locate upload button");
		else System.out.println("Found upload button");
 		driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
 		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", button);
	    System.out.print("clicked upload...");
	    
	}

	@Then("I should see the data from my CSV file on my home page")
	public void i_should_see_the_data_from_my_CSV_file_on_my_home_page() throws InterruptedException {
		Thread.sleep(200);
		WebDriverWait waiter = new WebDriverWait(driver, 150);
		System.out.println("looking for data...");	
		waiter.until( ExpectedConditions.presenceOfElementLocated(By.xpath("//table/tbody[@id='stockListBody']")) );
		List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
		if (driver.findElement(By.cssSelector("#cancelCSV")).isDisplayed())
			driver.findElement(By.cssSelector("#cancelCSV")).click();	
		waiter.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("#portfolioButton"))));
		System.out.println("First stock: " + rows.get(0).findElement(By.xpath("./td")).getAttribute("innerText"));
		assertTrue(rows.get(0).findElement(By.xpath("./td")).getAttribute("innerText").contentEquals("Tesla"));
		assertTrue(rows.get(1).findElement(By.xpath("./td")).getAttribute("innerText").contentEquals("Nikola Corporation"));
		assertTrue(rows.get(2).findElement(By.xpath("./td")).getAttribute("innerText").contentEquals("Google"));
		assertTrue(rows.get(3).findElement(By.xpath("./td")).getAttribute("innerText").contentEquals("Apple"));
		assertTrue(rows.get(4).findElement(By.xpath("./td")).getAttribute("innerText").contentEquals("Twitter"));
		 
	}

	// SCENARIO TWO - VALID FILE, INVALID DATA 
	
	@When("I provide a valid CSV file with invalid data")
	public void i_provide_a_valid_CSV_file_with_invalid_data() {
		File file = new File("files/invaliddataformat.csv");
		String path = file.getAbsolutePath();
		WebElement chooseFile = driver.findElement(By.cssSelector("#csvFile"));
		System.out.println("Sent: " + file.getAbsolutePath());
	    chooseFile.sendKeys(path);
	    WebElement button = driver.findElement(By.xpath("//*[@id='csvForm']/button"));
 		if (driver.findElements(By.xpath("//*[@id='csvForm']/button")).size()==0) 
	    	System.out.println("Can't locate upload button");
		else System.out.println("Found upload button");
 		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", button);
	    System.out.println("uploading...");
	}

	@Then("I should see an error message for incorrect or empty data")
	public void i_should_see_an_error_message_for_incorrect_or_empty_data() throws InterruptedException {
		Thread.sleep(200);
	    WebElement error = driver.findElement(By.cssSelector("#csvError"));
	    System.out.println("ERROR: " + error.getAttribute("innerHTML"));
	    assertTrue("Error: " + error.getAttribute("innerHTML"), error.getAttribute("innerHTML").contains("Incorrect data or empty file"));
	}

	// SCENARIO THREE - VALID FILE, EMPTY DATA
	
	@When("I provide a valid CSV file with empty data")
	public void i_provide_a_valid_CSV_file_with_empty_data() {
		File file = new File("files/empty.csv");
		String path = file.getAbsolutePath();
		WebElement chooseFile = driver.findElement(By.cssSelector("#csvFile"));
		System.out.println("Sent: " + file.getAbsolutePath());
	    chooseFile.sendKeys(path);
	    WebElement button = driver.findElement(By.xpath("//*[@id='csvForm']/button"));
 		if (driver.findElements(By.xpath("//*[@id='csvForm']/button")).size()==0) 
	    	System.out.println("Can't locate upload button");
		else System.out.println("Found upload button");
 		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", button);
	    System.out.println("uploading...");
	}
	
	// SCENARIO FOUR - INVALID FILE
	
	@When("I provide an invalid CSV file")
	public void i_provide_an_invalid_CSV_file() {
		WebElement button = driver.findElement(By.xpath("//*[@id='csvForm']/button"));
 		if (driver.findElements(By.xpath("//*[@id='csvForm']/button")).size()==0) 
	    	System.out.println("Can't locate upload button");
		else System.out.println("Found upload button");
 		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", button);
	    System.out.println("uploading...");
	}

	@Then("I should see an error message for nonexistent file")
	public void i_should_see_an_error_message_for_nonexistent_file() throws InterruptedException {
		Thread.sleep(200);
		WebElement error = driver.findElement(By.cssSelector("#csvError"));
		System.out.println("ERROR: " + error.getAttribute("innerHTML"));
	    assertTrue("Error: " + error.getAttribute("innerHTML"), error.getAttribute("innerHTML").contains("File does not exist"));
	}
}
