package driver;

import java.util.ArrayList;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import utilities.ConfigurationReader;

public final class DriverFactory {
	private static final Logger logger = LogManager.getLogger(DriverFactory.class);
	private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

	private static List<WebDriver> storedDrivers = new ArrayList<>();

	static {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				storedDrivers.forEach(WebDriver::quit);
			}
		});
	}

	private DriverFactory() {}

	public static WebDriver getDriver() {
		return drivers.get();
	}

	public static void addDriver(WebDriver driver) {
		storedDrivers.add(driver);
		drivers.set(driver);
	}

	public static void removeDriver() {
		drivers.get().close();
		storedDrivers.remove(drivers.get());
		drivers.remove();
	}


	public static synchronized WebDriver createDriver() {
		WebDriver driver = null;
		String browser = System.getProperty("browser");
		browser = browser == null ? ConfigurationReader.getProperty("browser") : browser;
		logger.info("Browser type :: "+browser);
		switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				return new ChromeDriver();
			case "chromeHeadless":
				WebDriverManager.chromedriver().setup();
				return new ChromeDriver(new ChromeOptions().setHeadless(true));
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				break;
			case "firefoxHeadless":
				WebDriverManager.firefoxdriver().setup();
				return new FirefoxDriver(new FirefoxOptions().setHeadless(true));
			case "ie":
				if (System.getProperty("os.name").toLowerCase().contains("mac"))
					throw new WebDriverException("You are operating Mac OS which doesn't support Internet Explorer");
				WebDriverManager.iedriver().setup();
				return new InternetExplorerDriver();
			case "edge":
				WebDriverManager.edgedriver().setup();
				return new EdgeDriver();
			case "safari":
				if (System.getProperty("os.name").toLowerCase().contains("windows"))
					throw new WebDriverException("You are operating Windows OS which doesn't support Safari");
				WebDriverManager.getInstance(SafariDriver.class).setup();
				return new SafariDriver();
			default:
				throw new RuntimeException("Illegal browser type!");
		}
		logger.info("Browser thread :: " + Thread.currentThread().getId());
		return driver;
	}
}
