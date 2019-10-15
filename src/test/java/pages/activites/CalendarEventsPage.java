package pages.activites;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;
import utilities.BrowserUtils;
import utilities.ConfigurationReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalendarEventsPage extends BasePage {
    @FindBy(css = "[title='Create Calendar event']")
    public WebElement createCalendarEventBtn;

    @FindBy(css = "a[title='Grid Settings']")
    public WebElement gridSettingsElement;

    @FindBy(css = "a[title='Reset']")
    public WebElement resetBtnElement;

    @FindBy(css = "[class='grid-header-cell__label']")
    public List<WebElement> headers;

    @FindBy(css = "[id^='date_selector_oro_calendar_event_form_start']")
    public WebElement startDate;
    @FindBy(css = "[id^='date_selector_oro_calendar_event_form_end']")
    public WebElement endDate;

    @FindBy(css = "[id^='time_selector_oro_calendar_event_form_start']")
    public WebElement startTime;

    @FindBy(css = "[id^='time_selector_oro_calendar_event_form_end']")
    public WebElement endTime;

    @FindBy(css = "select[class='ui-timepicker-wrapper']")
    public WebElement timeDropdown;

    @FindBy(css = "select[class='ui-datepicker-month']")
    public WebElement monthDropdown;

    @FindBy(css = "select[class='ui-datepicker-year']")
    public WebElement yearDropdown;

    @FindBy(css = "[id^='recurrence-repeat-view']")
    public WebElement repeatCheckbox;


    public void selectGridSetting(String name, boolean yesOrNo) {
        //click on grid options
        waitUntilLoaderScreenDisappear();
        gridSettingsElement.click();
        //create locator for grid option based on the name
        String locator = "//td//label[text()='" + name + "']/../following-sibling::td//input";
        //find element
        //you can also call Driver.get()
        WebElement gridOption = DriverFactory.getDriver().findElement(By.xpath(locator));
        //if param yesOrNo is true, and checkbox is not selected yet
        //click on it
        //or
        //ckeckbox is selected and you want to unselect it
        if ((yesOrNo == true && !gridOption.isSelected()) || (
                yesOrNo == false && gridOption.isSelected())) {
            gridOption.click();
        }
    }

    public boolean verifyHeaderExists(String headerNameOrColumnName) {
        for (WebElement tableHeader : headers) {
            if (tableHeader.getText().equalsIgnoreCase(headerNameOrColumnName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simple method that can select start or end date on create calendar event page
     *
     * @param date       format MM/dd/yyy for example 12/12/2019
     * @param startOrEnd which date to click on start or end
     */
    public void selectStartOrEndDate(String date, String startOrEnd) {
        waitUntilLoaderScreenDisappear();
        LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(date.lastIndexOf("/") + 1)),
                Integer.parseInt(date.substring(0, date.indexOf("/"))),
                Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"))));

        String month = DateTimeFormatter.ofPattern("MMM").format(ld);
        int year = ld.getYear();
        int day = ld.getDayOfMonth();


        //locator for day
        String dayLocator = "//a[@class='ui-state-default' and text()='" + day + "']";

        //click on start or end date
        if (startOrEnd.equalsIgnoreCase("start")) {
            browserUtils.waitForVisibility(startDate, 5);
            startDate.click();
        } else {
            browserUtils.waitForVisibility(endDate, 5);
            endDate.click();
        }

        //select month
        new Select(monthDropdown).selectByVisibleText(month);


        //select year
        new Select(yearDropdown).selectByVisibleText(year + "");

        //select day
        DriverFactory.getDriver().findElement(By.xpath(dayLocator)).click();
    }

    public void selectTomorrowDay() {
        int day = LocalDate.now().plusDays(1).getDayOfMonth();
        int month = LocalDate.now().plusDays(1).getMonth().getValue();
        waitUntilLoaderScreenDisappear();
        startDate.click();
        Select monthSelect = new Select(monthDropdown);
        monthSelect.selectByIndex(month - 1);
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        DriverFactory.getDriver().findElement(By.xpath(dayLocator)).click();
    }


    public void selectADay(int plusDays) {
        int day = LocalDate.now().plusDays(plusDays).getDayOfMonth();
        int month = LocalDate.now().plusDays(plusDays).getMonth().getValue();
        waitUntilLoaderScreenDisappear();
        startDate.click();
        Select monthSelect = new Select(monthDropdown);
        monthSelect.selectByIndex(month - 1);
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        DriverFactory.getDriver().findElement(By.xpath(dayLocator)).click();
    }

    public void selectTodaysDate() {
        int day = LocalDate.now().getDayOfMonth();
        startDate.click();
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        try {
            DriverFactory.getDriver().findElement(By.xpath(dayLocator)).click();
        } catch (Exception e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            DriverFactory.getDriver().findElement(By.xpath(dayLocator)).click();
        }
    }

    public void selectStartTime(int plusHours) {
        String time = LocalDateTime.now().plusHours(plusHours).format(DateTimeFormatter.ofPattern("h:00 a"));
        waitUntilLoaderScreenDisappear();
        String startTimeToSelect = "(//li[text()='" + time + "'])[1]";
        startTime.click();
        new WebDriverWait(DriverFactory.getDriver(), 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(startTimeToSelect)));
        DriverFactory.getDriver().findElement(By.xpath(startTimeToSelect)).click();
    }

    public void selectStartTime(String time) {
        waitUntilLoaderScreenDisappear();
        String startTimeToSelect = "(//li[text()='" + time + "'])[1]";
        startTime.click();
        new WebDriverWait(DriverFactory.getDriver(), 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(startTimeToSelect)));
        DriverFactory.getDriver().findElement(By.xpath(startTimeToSelect)).click();
    }

    public void selectEndTime(String time) {
        waitUntilLoaderScreenDisappear();
        String endTimeToSelect = "(//li[text()='" + time + "'])[2]";
        startTime.click();
        DriverFactory.getDriver().findElement(By.xpath(endTimeToSelect)).click();
    }

    public long differenceBetweenStartTimeAndEndTime() {
        LocalTime actualStartTime = LocalTime.parse(startTime.getAttribute("value"), DateTimeFormatter.ofPattern("h:mm a"));
        try {
            new WebDriverWait(DriverFactory.getDriver(), 3).until(ExpectedConditions.invisibilityOf(startTime));
        } catch (Exception e) {
            System.out.println(e);
        }
        LocalTime actualEndTime = LocalTime.parse(endTime.getAttribute("value"), DateTimeFormatter.ofPattern("h:mm a"));
        return ChronoUnit.HOURS.between(actualStartTime, actualEndTime);
    }

    public void clickOnCreateCalendarEvent() {
        waitUntilLoaderScreenDisappear();
        browserUtils.waitForStaleElement(createCalendarEventBtn);
        browserUtils.waitForClickablility(createCalendarEventBtn, Integer.valueOf(ConfigurationReader.getProperty("SHORT_WAIT")));
        createCalendarEventBtn.click();
    }

    public String getStartDate() {
        return startDate.getAttribute("value");
    }

    public String getEndDate() {
        return endDate.getAttribute("value");
    }

    public String getStartTime() {
        return startTime.getAttribute("value");
    }

    public String getEndTime() {
        return endTime.getAttribute("value");
    }

    public List<String> getTableHeaders() {
        waitUntilLoaderScreenDisappear();
        return browserUtils.getElementsText(headers);
    }

}
