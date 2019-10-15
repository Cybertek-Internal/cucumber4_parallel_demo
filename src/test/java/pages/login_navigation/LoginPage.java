package pages.login_navigation;

import driver.DriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import base.BasePage;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;

public class LoginPage extends BasePage {

    @FindBy(id = "prependedInput")
    @CacheLookup
    public WebElement userNameElement;

    @FindBy(name = "_password")
    @CacheLookup
    public WebElement passwordElement;

    @FindBy(id = "_submit")
    public WebElement loginButtonElement;

    @FindBy(className = "custom-checkbox__icon")
    public WebElement rememberMeElement;

    @FindBy(partialLinkText = "Forgot your password?")
    public WebElement forgotPasswordElement;

    @FindBy(tagName = "h2")
    public WebElement titleElement;

    @FindBy(css = "[class='alert alert-error'] > div")
    public WebElement errorMessageElement;


    public void login(String username, String password) {
        userNameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButtonElement.click();
    }

    public void login() {
        String username = ConfigurationReader.getProperty("storemanagerusername");
        String password = ConfigurationReader.getProperty("storemanagerpassword");
        userNameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButtonElement.click();
    }


    public void login(String role) {
        String username = "";
        String password = "";
        if (role.equalsIgnoreCase("driver")) {
            username = ConfigurationReader.getProperty("driverusername");
            password = ConfigurationReader.getProperty("driverpassword");
        } else if (role.equalsIgnoreCase("store manager")) {
            username = ConfigurationReader.getProperty("storemanagerusername");
            password = ConfigurationReader.getProperty("storemanagerpassword");
        } else if (role.equalsIgnoreCase("sales manager")) {
            username = ConfigurationReader.getProperty("salesmanagerusername");
            password = ConfigurationReader.getProperty("salesmanagerpassword");
        }
        userNameElement.sendKeys(username);
        passwordElement.sendKeys(password);
        loginButtonElement.click();
    }

    public String getErrorMessage() {
        return errorMessageElement.getText();
    }

    public void clickRememberMe() {
        browserUtils.waitForClickablility(rememberMeElement, Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
        if (!rememberMeElement.isSelected()) {
            rememberMeElement.click();
        }
    }


    public void goToLandingPage() {
        DriverFactory.getDriver().get(ConfigurationReader.getProperty("url" + ConfigurationReader.getProperty("environment")));
    }
}
