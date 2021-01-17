package cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
//@CucumberOptions(strict = true)
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/register.feature"})
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/login.feature"})
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/lockout.feature"})
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/loginMobile.feature"})
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/buttonText.feature"})
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/viewStockTable.feature"})
//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/addStockTable.feature"})
@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/graph.feature"})
// Brenna start














// Brenna end



// Asheesh Start

//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/hidePassword.feature"})

//@CucumberOptions(strict = true, features= {"src/test/resources/cucumber/addStock.feature"})









// Asheesh end






// Maryam















//Maryam


// York















// York

public class RunCucumberTests {

	@BeforeClass
	public static void setup() {
		WebDriverManager.chromedriver().setup();
	}

}
