package step_definitions;

import cucumber.api.java.en.Then;
import org.junit.Assert;
import pages.login_navigation.LoginPage;

public class TopMenuStepDefinitions {

    private LoginPage loginPage;

    public TopMenuStepDefinitions(LoginPage loginPage){
        this.loginPage=loginPage;
    }


    @Then("user navigates to {string} and {string}")
    public void user_navigates_to_and(String tab, String module) {
        loginPage.navigateToModule(tab, module);
    }

    @Then("user name should be {string}")
    public void user_name_should_be(String expected) {
        Assert.assertEquals(expected, loginPage.getUserMenuName());
    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expected) {
        Assert.assertEquals(expected, loginPage.getPageTitle());
    }

}
