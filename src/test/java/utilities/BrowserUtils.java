package utilities;

import driver.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.sql.Driver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class BrowserUtils {
    private static final Logger logger = LogManager.getLogger(BrowserUtils.class);

    /**
     * @param expectedResult
     * @param actualResult   Verifies if two strings are equals.
     */
    public void verifyEquals(String expectedResult, String actualResult) {
        if (expectedResult.equals(actualResult)) {
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
            System.out.println("Expected result: " + expectedResult);
            System.out.println("Actual result: " + actualResult);
        }
    }

    /**
     * This method will put on pause execution
     *
     * @param seconds
     */
    public void waitPlease(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            logger.error(e);
            System.out.println(e.getMessage());
        }

    }

    /**
     * @param page
     * @param driver This method will open example page based on link name
     */
    public void openPage(String page, WebDriver driver) {
        //we will find all examples on the home page
        List<WebElement> listOfExamples = driver.findElements(By.tagName("a"));
        for (WebElement example : listOfExamples) {
            if (example.getText().contains(page)) {
                example.click();
                break;
            }
        }
    }

    public void verifyIsDisplayed(WebElement element) {
        if (element.isDisplayed()) {
            System.out.println("PASSED");
            System.out.println(element.getText() + ": is visible");
        } else {
            System.out.println("FAILED");
            System.out.println(element.getText() + ": is not visible!");
        }
    }

    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param driver
     * @param by
     * @param attempts
     */
    public void clickWithWait(WebDriver driver, By by, int attempts) {
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                driver.findElement(by).click();
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                logger.error(e);
                //print attempt
                logger.error("Attempt :: " + ++counter);
                //wait for 1 second, and try to click again
                waitPlease(1);
            }
        }
    }

    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param by
     * @param attempts
     */
    public void clickWithWait(By by, int attempts) {
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                clickWithJS(DriverFactory.getDriver().findElement(by));
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                logger.error(e);
                //print attempt
                logger.error("Attempt :: " + ++counter);
                //wait for 1 second, and try to click again
                waitPlease(1);
            }
        }
    }


    /*
     * switches to new window by the exact title
     */
    public void switchToWindow(String targetTitle) {
        String origin = DriverFactory.getDriver().getWindowHandle();
        for (String handle : DriverFactory.getDriver().getWindowHandles()) {
            DriverFactory.getDriver().switchTo().window(handle);
            if (DriverFactory.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        DriverFactory.getDriver().switchTo().window(origin);
    }

    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    public void hover(WebElement element) {
        Actions actions = new Actions(DriverFactory.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * return a list of string from a list of elements
     * text
     *
     * @param list of webelements
     * @return
     */
    public List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    /**
     * Extracts text from list of elements matching the provided locator into new List<String>
     *
     * @param locator
     * @return list of strings
     */
    public List<String> getElementsText(By locator) {
        List<WebElement> elems = DriverFactory.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    /**
     * Performs a pause
     *
     * @param seconds
     */
    public void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element
     * @param timeToWaitInSec
     * @return
     */
    public WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element matching the locator to be visible on the page
     *
     * @param locator
     * @param timeout
     * @return
     */
    public WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for provided element to be clickable
     *
     * @param element
     * @param timeout
     * @return
     */
    public WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element matching the locator to be clickable
     *
     * @param locator
     * @param timeout
     * @return
     */
    public WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     *
     * @param by
     * @throws AssertionError if the element matching the provided locator is not found or not displayed
     */
    public void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, DriverFactory.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            logger.error(e);
            e.printStackTrace();
            Assert.fail("Element not found: " + by);
        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param by
     * @throws AssertionError the element matching the provided locator is displayed
     */
    public void verifyElementNotDisplayed(By by) {
        try {
            Assert.assertFalse("Element should not be visible: " + by, DriverFactory.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Verifies whether the element is displayed on page
     *
     * @param element
     * @throws AssertionError if the element is not found or not displayed
     */
    public void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            logger.error(":::Element not found:::");
            Assert.fail("Element not found: " + element);
        }
    }

    /**
     * Waits for element to be not stale
     *
     * @param element
     */
    public void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            try {
                element.isDisplayed();
                break;
            } catch (StaleElementReferenceException st) {
                y++;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }

    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public void clickWithJS(WebElement element) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element
     */
    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public void doubleClick(WebElement element) {
        new Actions(DriverFactory.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element
     * @param attributeName
     * @param attributeValue
     */
    public void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Highlighs an element by changing its background and border color
     *
     * @param element
     */
    public void highlight(WebElement element) {
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        waitFor(1);
        ((JavascriptExecutor) DriverFactory.getDriver()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * Checks or unchecks given checkbox
     *
     * @param element
     * @param check
     */
    public void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * attempts to click on provided element until given time runs out
     *
     * @param element
     * @param timeout
     */
    public void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                break;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param element
     */
    public void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) DriverFactory.getDriver();
        jse.executeScript(command, element);
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param command
     */
    public void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor) DriverFactory.getDriver();
        jse.executeScript(command);
    }

    /*
     * takes screenshot
     * @param name
     * take a name of a test and returns a path to screenshot takes
     */
    public String getScreenshot(String name) {
        // name the screenshot with the current date time to avoid duplicate name
        //for windows users or if you cannot get a screenshot
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MMdd_hh_mm_ss_a"));
        // TakesScreenshot ---> interface from selenium which takes screenshots
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        //if screenshot doesn't work
        //try to provide hardcoded path
//        String target = "/Users/studio2/IdeaProjects/Spring2019FinalTestNGFramework/screenshots/" + name + date + ".png";

        File finalDestination = new File(target);

        // save the screenshot to the path given
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }


    public void waitForPresenceOfElement(By by, long time) {
        new WebDriverWait(DriverFactory.getDriver(), time).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForsStaleness(By by) {
        new WebDriverWait(DriverFactory.getDriver(), Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT"))).
                until(ExpectedConditions.stalenessOf(DriverFactory.getDriver().findElement(by)));
    }

    /**
     * Retrieves the text of the element. If the element isn't present, a null
     * value will be returned.
     *
     * @return String: the text of the element
     */
    public String text(WebElement webElement) {
        return webElement.getText();
    }

    /**
     * Retrieves all attributes of the element. If the element isn't present, or
     * the attributes can't be accessed, a null value will be returned.
     *
     * @return String: the value of the css attribute
     */
    public Map<String, String> allAttributes(WebElement webElement) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
            return (Map<String, String>) js.executeScript(
                    "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                    webElement);
        } catch (NoSuchMethodError | Exception e) {
            logger.warn(e);
            return null;
        }
    }

    /**
     * Determines whether the element is present or not.
     *
     * @return Boolean: whether the element is present or not
     */
    public boolean present(WebElement element) {
        boolean isPresent = false;
        try {
            element.getText();
            isPresent = true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            logger.info(e);
        }
        return isPresent;
    }
}
