package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SharedDriver {

	public SharedDriver() {
		if (DriverFactory.getDriver() == null) {
			WebDriver driver = DriverFactory.createDriver();
			DriverFactory.addDriver(driver);
		}
	}
}
