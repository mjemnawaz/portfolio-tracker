package cucumber;

import static org.junit.Assert.assertTrue;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValueChange {
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
	
	public ValueChange() {
		driver = Init.getDriver();
	}
	
	// Scenario 1: Adding a stock that should lead to green percent change with up arrow
	
	
	@When("I enter today for the day purchased")
	public void i_enter_today_for_the_day_purchased() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement datePurchased = driver.findElement(By.id("datePurchased"));
		datePurchased.sendKeys(dtf.format(java.time.LocalDate.now(ZoneId.of("America/Los_Angeles"))).toString());
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Then("I should see a percent change in {string} with an {string} arrow")
	public void i_should_see_a_percent_change_in_with_an_arrow(String string, String string2) {
		 
		WebElement arrow = driver.findElement(By.id("dirArrow"));
		
		String color = arrow.getCssValue("color");
		String expectedClass = "fa fa-caret-" + string2;
		String actualClass = arrow.getAttribute("class");
		
		System.out.println(actualClass);
		System.out.println(color);
		
		assertTrue(color.equals(string) && actualClass.equals(expectedClass));
		
	}
	
	// Scenario 2
	
	@When("I enter today for the day sold")
	public void i_enter_today_for_the_day_sold() {
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement dateSold = driver.findElement(By.id("dateSold"));
		dateSold.sendKeys(dtf.format(java.time.LocalDate.now(ZoneId.of("America/Los_Angeles"))).toString());
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
