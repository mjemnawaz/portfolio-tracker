package cucumber;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GraphScenarios {
	
	private final WebDriver driver;
	private String dateRangeStart = "";
	private List<String> vals = new ArrayList<String>();
	private int interval = 0;
	
	public GraphScenarios() {
		driver = Init.getDriver();
	}
	
	// set up for all scenarios
	// NOTE - beforehand make sure there are at least two stocks in portfolio for user:randouser, pass:randopass
	
	@Given("I am logged in and I am on the home page with username {string} and password {string}")
	public void i_am_logged_in_and_I_am_on_the_home_page_with_username_and_password(String username, String password) {
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
	
	// SCENARIO ONE
	
	@Then("I should see the default {int} month portfolio graph")
	public void i_should_see_the_default_month_portfolio_graph(Integer int1) {
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/thead/tr/th[2]")).getAttribute("innerHTML").contains("Portfolio"));
	    assertTrue(driver.findElement(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr[1]/td[1]")).getAttribute("innerHTML").contains("Aug"));
	}
	
	// SCENARIO TWO
	
	@When("I click on the S&P Checkbox")
	public void i_click_on_the_S_P_Checkbox() {
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr"));
		System.out.println("Dates: " + rows.size());
		dateRangeStart = driver.findElement(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr[2]/td[1]")).getAttribute("innerText");
		System.out.println("3 months ago: " + dateRangeStart);
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.id("sAndPCheck")).click();
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should see the S&P graph")
	public void i_should_see_the_S_P_graph() {
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(driver.findElement(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/thead/tr/th[2]")).getAttribute("innerHTML").contains("^GSPC"));
	}
	
	// SCENARIO THREE

	@Then("I should see the portfolio graph with the correct range")
	public void i_should_see_the_portfolio_graph_with_the_correct_range() {
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Date of the first transaction: " + driver.findElement(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr[1]/td[1]")).getAttribute("innerText"));
		assertTrue(!dateRangeStart.equals(driver.findElement(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr[1]/td[1]")).getAttribute("innerText")));
	}
	
	// SCENARIO FOUR
	
	@When("I click on one stock to include in the graph")
	public void i_click_on_one_stock_to_include_in_the_graph() {
		driver.findElement(By.id("defaultCheck1")).click();
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr"));
		for (int i = 0; i < rows.size(); i++) 
			vals.add(rows.get(i).findElement(By.xpath("./td[2]")).getAttribute("innerText"));
	}

	@When("I click on another stock to include in the graph")
	public void i_click_on_another_stock_to_include_in_the_graph() {
		driver.findElement(By.id("defaultCheck2")).click();
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("I should see the portfolio graph update")
	public void i_should_see_the_portfolio_graph_update() {
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr"));
		boolean diff = false;
		for (int i =0; i < rows.size(); i++) {
			if (!rows.get(i).findElement(By.xpath("./td[2]")).getAttribute("innerText").equals(vals.get(i)))
				diff = true;
		}
		assertTrue(diff);
	}
	
	// SCENARIO FIVE
	
	@When("I change the increments on the graph from daily to weekly")
	public void i_change_the_increments_on_the_graph_from_daily_to_weekly() throws ParseException {
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr"));
		String strDate1 = rows.get(0).findElement(By.xpath("./td[1]")).getAttribute("innerText");
		String strDate2 = rows.get(1).findElement(By.xpath("./td[1]")).getAttribute("innerText");
		System.out.println(strDate1 + " and " + strDate2);
		Date date1 = new SimpleDateFormat("MMM dd, yyyy").parse(strDate1);
		Date date2 = new SimpleDateFormat("MMM dd, yyyy").parse(strDate2);
		long difference = date2.getTime() - date1.getTime();
	    interval = (int)(difference / (1000*60*60*24));
		Select dropdown = new Select(driver.findElement(By.id("xAxis")));
		dropdown.selectByValue("1");
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("the intervals should go from {int} to {int}")
	public void the_intervals_should_go_from_to(Integer int1, Integer int2) throws ParseException {
		List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"chart_div\"]/div/div[1]/div/div/table/tbody/tr"));
		String strDate1 = rows.get(0).findElement(By.xpath("./td[1]")).getAttribute("innerText");
		String strDate2 = rows.get(1).findElement(By.xpath("./td[1]")).getAttribute("innerText");
		System.out.println(strDate1 + " and " + strDate2);
		Date date1 = new SimpleDateFormat("MMM dd, yyyy").parse(strDate1);
		Date date2 = new SimpleDateFormat("MMM dd, yyyy").parse(strDate2);
		long difference = date2.getTime() - date1.getTime();
		assertTrue(interval==int1);
	    assertTrue(((int)(difference / (1000*60*60*24)))==int2);
	}
	
}
