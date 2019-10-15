package base;
import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;

import java.sql.Driver;

public abstract class BasePage {
    //we don't want to access these variables outside
    private static final Logger logger = LogManager.getLogger();
    protected BrowserUtils browserUtils = new BrowserUtils();


    @FindBy(css = "div[class='loader-mask shown']")
    @CacheLookup
    protected WebElement loaderMask;

    @FindBy(css = "h1[class='oro-subtitle']")
    protected WebElement pageSubTitle;

    @FindBy(css = "#user-menu > a")
    protected WebElement userMenuName;


    public BasePage() {
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }


    /**
     * @return page name, for example: Dashboard
     */
    public String getPageSubTitle() {
        //ant time we are verifying page name, or page subtitle, loader mask appears
        waitUntilLoaderScreenDisappear();
        browserUtils.waitForStaleElement(pageSubTitle);
        return pageSubTitle.getText();
    }


    /**
     * Waits until loader screen present. If loader screen will not pop up at all,
     * NoSuchElementException will be handled  bu try/catch block
     * Thus, we can continue in any case.
     */
    public void waitUntilLoaderScreenDisappear() {
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
            wait.until(ExpectedConditions.invisibilityOf(loaderMask));
            logger.info("Loader mask gone...");
        } catch (Exception e) {
            logger.error("Loader mask doesn't present.");
            logger.error(e);
        }
    }

    /**
     * This method will navigate user to the specific module in vytrack application.
     * For example: if tab is equals to Activities, and module equals to Calls,
     * Then method will navigate user to this page: http://qa2.vytrack.com/call/
     *
     * @param tab
     * @param module
     */
    public void navigateToModule(String tab, String module) {
        String tabLocator = "//span[normalize-space()='" + tab + "' and contains(@class, 'title title-level-1')]";
        String moduleLocator = "//span[normalize-space()='" + module + "' and contains(@class, 'title title-level-2')]";
        try {
            browserUtils.waitForClickablility(By.xpath(tabLocator), Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
            WebElement tabElement = DriverFactory.getDriver().findElement(By.xpath(tabLocator));
            new Actions(DriverFactory.getDriver()).moveToElement(tabElement).pause(200).doubleClick(tabElement).build().perform();
        } catch (Exception e) {
            logger.error("Failed to click on :: "+tab);
            logger.error(e);
            browserUtils.clickWithWait(By.xpath(tabLocator), Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
        }
        try {
            browserUtils.waitForPresenceOfElement(By.xpath(moduleLocator), Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
            browserUtils.waitForVisibility(By.xpath(moduleLocator), Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
            browserUtils.scrollToElement(DriverFactory.getDriver().findElement(By.xpath(moduleLocator)));
            DriverFactory.getDriver().findElement(By.xpath(moduleLocator)).click();
        } catch (Exception e) {
            logger.error("Failed to click on :: "+module);
            logger.error(e);
            browserUtils.waitForStaleElement(DriverFactory.getDriver().findElement(By.xpath(moduleLocator)));
            browserUtils.clickWithTimeOut(DriverFactory.getDriver().findElement(By.xpath(moduleLocator)),  Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
        }
    }

    public String getUserMenuName(){
        waitUntilLoaderScreenDisappear();
        return userMenuName.getText();
    }

    public String getPageTitle(){
        waitUntilLoaderScreenDisappear();
        return DriverFactory.getDriver().getTitle();
    }

}
