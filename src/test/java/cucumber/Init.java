package cucumber;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Init {
    private static WebDriver driver;
    private static WebDriver mobileDriver;

    public static WebDriver getDriver() {
        return driver;
    }
    
    public static WebDriver getMobileDriver() {
    	return mobileDriver;
    }

    @Before
    public void beforeSuite() {

        driver = new ChromeDriver();
        
        Map<String, String> mobileEmulation = new HashMap<>();

		mobileEmulation.put("deviceName", "iPhone X");

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

		mobileDriver = new ChromeDriver(chromeOptions);
    }

    @After
    public void afterSuite() {
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
		mobileDriver.quit();
    }
}
