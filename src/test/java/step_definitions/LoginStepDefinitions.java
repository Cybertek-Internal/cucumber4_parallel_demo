package step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pages.login_navigation.LoginPage;
import utilities.ConfigurationReader;

import java.util.Map;

public class LoginStepDefinitions {
    private LoginPage loginPage;

    public LoginStepDefinitions(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @Given("user is on the landing page")
    public void user_is_on_the_landing_page() {
        loginPage.goToLandingPage();
    }

    @Then("user logs in as a store manager")
    public void user_logs_in_as_a_store_manager() {
        String username = ConfigurationReader.getProperty("storemanagerusername");
        String password = ConfigurationReader.getProperty("storemanagerpassword");
        loginPage.login(username, password);
    }

    @Then("user verifies that {string} page name is displayed")
    public void user_verifies_that_page_name_is_displayed(String expected) {
        Assert.assertEquals(expected, loginPage.getPageSubTitle());
    }

    @Then("user logs in with {string} username and {string} password")
    public void user_logs_in_with_username_and_password(String string, String string2) {
        loginPage.login(string, string2);
    }

    @Then("user verifies that {string} warning message is displayed")
    public void user_verifies_that_warning_message_is_displayed(String expected) {
        Assert.assertEquals(expected, loginPage.getErrorMessage());
    }

    @Then("user logs in as a driver")
    public void user_logs_in_as_a_driver() {
        String username = ConfigurationReader.getProperty("driverusername");
        String password = ConfigurationReader.getProperty("driverpassword");
        loginPage.login(username, password);
    }

    @When("user logs in as a {string}")
    public void user_logs_in_as_a(String role) {
        loginPage.login(role);
    }

    @Given("user logs in with following credentials")
    public void user_logs_in_with_following_credentials(Map<String, String> values) {
        System.out.println(values);
        loginPage.login(values.get("username"), values.get("password"));
    }

}
