package step_definitions;


import cucumber.api.java.en.Then;
import org.junit.Assert;
import pages.activites.CalendarEventsPage;

import java.util.List;

public class CalendarEventsStepDefinitions {
    private CalendarEventsPage calendarEventsPage;

    public CalendarEventsStepDefinitions(CalendarEventsPage calendarEventsPage) {
        this.calendarEventsPage = calendarEventsPage;
    }

    @Then("following table headers should be displayed")
    public void following_table_headers_should_be_displayed(List<String> values) {
        Assert.assertEquals(values, calendarEventsPage.getTableHeaders());
    }
}
