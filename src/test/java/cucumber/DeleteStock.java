package cucumber;

import static org.junit.Assert.assertTrue;

import java.util.List;

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

public class DeleteStock {
	
	private final WebDriver driver;
	
	public DeleteStock() {
		driver = Init.getDriver();
	}
	
	int num = 0;
	
	@When("I have CSV holdings loaded in")
	public void i_have_csv_holdings_loaded_in() throws InterruptedException {
		Thread.sleep(200);
		WebDriverWait waiter = new WebDriverWait(driver, 150);
		System.out.println("looking for data...");	
		waiter.until( ExpectedConditions.presenceOfElementLocated(By.xpath("//table/tbody[@id='stockListBody']")) );
		List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
		System.out.println("First stock: " + rows.get(0).findElement(By.xpath("./td")).getAttribute("innerText"));
		if (driver.findElement(By.cssSelector("#cancelCSV")).isDisplayed())
			driver.findElement(By.cssSelector("#cancelCSV")).click();	
		waiter.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("#portfolioButton"))));
		System.out.println("Number of stocks: " + driver.findElements(By.cssSelector("#stockListBody tr")).size());
		num = driver.findElements(By.cssSelector("#stockListBody tr")).size();
	}
	
	@When("I click the button for deletion")
	public void i_click_the_button_for_deletion() {
		WebDriverWait waiter = new WebDriverWait(driver, 150);
		waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#stockListBody .btn-outline-danger")));
		List<WebElement> deleteButton = driver.findElements(By.cssSelector("#stockListBody .btn-outline-danger"));
		assertTrue(deleteButton.size() > 0);
		deleteButton.get(0).click();
	}
	
	@When("I click the button for confirmation of deletion")
	public void i_click_the_button_for_confimation_of_deletion() {
		WebDriverWait waiter = new WebDriverWait(driver, 150);
		waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#confirmDelete")));
		List<WebElement> deleteButton = driver.findElements(By.cssSelector("#confirmDelete"));
		assertTrue(deleteButton.size() > 0);
		deleteButton.get(0).click();
	}
	
	@Then("there should be one less stock in the portfolio")
	public void there_should_be_one_less_stock_in_the_portfolio() {
		WebDriverWait waiter = new WebDriverWait(driver, 5000);
		waiter.until( ExpectedConditions.presenceOfElementLocated(By.cssSelector("#stockListBody tr")) );
		System.out.println("looking for data... " + num + " to " + driver.findElements(By.cssSelector("#stockListBody tr")).size());
		assertTrue(num > driver.findElements(By.cssSelector("#stockListBody tr")).size());
		List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
		System.out.println("First stock: " + rows.get(0).findElement(By.xpath("./td")).getAttribute("innerText"));
	}

	
}
