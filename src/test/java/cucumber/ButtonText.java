package cucumber;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ButtonText {
	
	private static final String ROOT_URL = "http://localhost:8080/index.jsp";
	
	private final WebDriver driver;
	
	public ButtonText() {
		driver = Init.getDriver();
	}
	
	// Scenario 1: -----------Checking that Upload Portfolio Modal has "Upload File" and "Cancel" buttons-----------------
	
	@When("I click on Upload Portfolio on my dashboard")
	public void i_click_on_Upload_Portfolio_on_my_dashboard() {
	    
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement portfolioModal = driver.findElement(By.id("portfolioCard"));
		
		portfolioModal.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Then("I should see the buttons {string} and {string}")
	public void i_should_see_the_buttons_and(String string, String string2) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement uploadButton = driver.findElement(By.id("portfolioButton"));
		
		String uploadText = uploadButton.getText();
		
		WebElement cancelButton = driver.findElement(By.id("cancelCSV"));
		
		String cancelText = cancelButton.getText();
		
		assertTrue("error in testing upload button text", string.equals(uploadText) && string2.equals(cancelText));
		
		
	}
	
	
	// ---------------------------------Scenario 2: Check button text in Add Stock Modal
	
	@When("I click on Add Stock on my dashboard")
	public void i_click_on_Add_Stock_on_my_dashboard() {
	   
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement addModal = driver.findElement(By.id("addCard"));
		
		addModal.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

	@Then("I should see buttons with text {string} and {string}")
	public void i_should_see_buttons_with_text_and(String string, String string2) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement addButton = driver.findElement(By.id("addStockButton"));
		
		String addText = addButton.getText();
		
		WebElement cancelButton = driver.findElement(By.id("addStockCancel"));
		
		String cancelText = cancelButton.getText();
		
		assertTrue("error in testing add stock button text", string.equals(addText) && string2.equals(cancelText));
		
		
	}
	
	
	// ------------------------------Scenario 3: Check text on view stock buttons
	
	@When("I click on View Stock on my dashboard")
	public void i_click_on_View_Stock_on_my_dashboard() {
	   
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement viewModal = driver.findElement(By.id("historyCard"));
		
		viewModal.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("I should see the following buttons {string} and {string}")
	public void i_should_see_the_following_buttons_and(String string, String string2) {
		
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement viewButton = driver.findElement(By.id("viewStockButton"));
		
		String viewText = viewButton.getText();
		
		WebElement cancelButton = driver.findElement(By.id("viewCancel"));
		
		String cancelText = cancelButton.getText();
		
		assertTrue("error in testing view stock button text", string.equals(viewText) && string2.equals(cancelText));
	    
	}
	
	
	//------------------ Scenario 4: Check buttons in delete modal for "Delete Stock" and "Cancel"
	
	@When("I click on the delete button in the row of user stocks")
	public void i_click_on_the_delete_button_in_the_row_of_user_stocks() {
	   
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement deleteButton = driver.findElement(By.id("deleteButton1"));
		
		deleteButton.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("I should see the buttons with {string} and {string}")
	public void i_should_see_the_buttons_with_and(String string, String string2) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement deleteButton = driver.findElement(By.id("confirmDelete"));
		
		String deleteText = deleteButton.getText();
		
		WebElement cancelButton = driver.findElement(By.id("cancelDelete"));
		
		String cancelText = cancelButton.getText();
		
		assertTrue("error in testing delete stock button text", string.equals(deleteText) && string2.equals(cancelText));
		
	}
	
	//------------------ Scenario 5:  Verify Date purchased and Date sold fields in Add Stock modal
	
	@Then("I should see two fields {string} and {string}")
	public void i_should_see_two_fields_and(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    
		WebElement datePurchased = driver.findElement(By.id("labelPurchase"));
		
		String datePurchasedText = datePurchased.getText();
		
		WebElement dateSold = driver.findElement(By.id("labelSold"));
		
		String dateSoldText = dateSold.getText();
	
		assertTrue("error checking date sold and date purchased", string.equals(datePurchasedText) && string2.equals(dateSoldText));
		
	}
	
	// ------------------- Scenario 6: Verify Select All and Deselect All buttons in My Stocks Table
	
	@Then("I should see the buttons {string} and {string} in the table header")
	public void i_should_see_the_buttons_and_in_the_table_header(String string, String string2) {
	    
		String selectButtonText = driver.findElement(By.id("userStockSelectAll")).getText();
		
		String deselectButtonText = driver.findElement(By.id("userStockDeSelectAll")).getText();
		
		assertTrue("error checking select all and deselect all buttons", string.equals(selectButtonText) && string2.equals(deselectButtonText));
		
	}
	
	
	// -------------------- Scenario 7: Verify button text in Confirm Deletion for View Stocks Table
	
	@When("I view the stock {string}")
	public void i_view_the_stock(String string) {
	    
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement viewModal = driver.findElement(By.id("historyCard"));
		
		viewModal.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		// now use the view stock search bar to search for AMZN
		
		WebElement tickerSearch = driver.findElement(By.id("stockHistory"));
		
		tickerSearch.sendKeys(string);
		
		// now click the View Stock button
		
		WebElement viewButton = driver.findElement(By.id("viewStockButton"));
		viewButton.click();
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		// now click the exit modal button
		
		
		WebElement cancelButton = driver.findElement(By.id("viewCancel"));
		cancelButton.click();
		
		
	}

	@When("I click on the delete button in the row of view stocks")
	public void i_click_on_the_delete_button_in_the_row_of_view_stocks() {
	    
		try {
			Thread.sleep(5000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Here");
		
		WebElement deleteButton = driver.findElement(By.id("removeHistoryButton1"));
		
		deleteButton.click();
		
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Then("I should see the a pop-up with the buttons {string} and {string}")
	public void i_should_see_the_a_pop_up_with_the_buttons_and(String string, String string2) {
	    
		try {
			Thread.sleep(2000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement deleteButton = driver.findElement(By.id("confirmRemoval"));
		
		String deleteText = deleteButton.getText();
		
		WebElement cancelButton = driver.findElement(By.id("cancelRemove"));
		
		String cancelText = cancelButton.getText();
		
		assertTrue("error in testing delete pop-up for view stocks table", string.equals(deleteText) && string2.equals(cancelText));
		
		
	}
	
	
	
	
}
