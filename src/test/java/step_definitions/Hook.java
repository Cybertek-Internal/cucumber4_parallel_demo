package step_definitions;


import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import driver.SharedDriver;
import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.activites.CalendarEventsPage;
import pages.fleet.VehiclesPage;
import pages.login_navigation.LoginPage;
import utilities.ConfigurationReader;

import java.util.concurrent.TimeUnit;

public class Hook {
    private static final Logger logger = LogManager.getLogger(Hook.class);
    private SharedDriver sharedDriver;

    public Hook(SharedDriver sharedDriver) {
        this.sharedDriver = sharedDriver;
    }

    @Before
    public void setup(Scenario scenario) {
        System.out.println(scenario.getSourceTagNames());
        System.out.println(scenario.getName());
        System.out.println("BEFORE");
        logger.warn("THREAD :: " + Thread.currentThread().getId());
        DriverFactory.getDriver().manage().window().maximize();
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        DriverFactory.getDriver().get(ConfigurationReader.getProperty("url" + ConfigurationReader.getProperty("environment")));
    }

    @After
    public void teardown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverFactory.getDriver();
            byte[] image = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            //will attach screenshot into report
            scenario.embed(image, "image/png");
        }
        DriverFactory.removeDriver();
    }

}
