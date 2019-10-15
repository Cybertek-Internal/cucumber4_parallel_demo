package pages.fleet;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;
import utilities.BrowserUtils;

public class VehiclesPage extends BasePage {
    @FindBy(xpath = "//label[text()='Page:']/following-sibling::ul//input")
    public WebElement pageNumber;

    @FindBy(css = "[title='Create Car']")
    public WebElement createACarBtn;

    @FindBy(css = "[id^='custom_entity_type_LicensePlate']")
    public WebElement licensePlate;

    @FindBy(css = "[id^='custom_entity_type_Driver']")
    public WebElement driverInput;

    @FindBy(css = "[id^='custom_entity_type_Location']")
    public WebElement location;

    @FindBy(css = "[id^='custom_entity_type_ModelYear']")
    public WebElement modelYear;

    @FindBy(css = "[id^='custom_entity_type_Color']")
    public WebElement color;

    @FindBy(css = "[id^='custom_entity_type_Power']")
    public WebElement power;

    @FindBy(css = "[class='btn btn-success action-button']")
    public WebElement saveAndClose;

    @FindBy(xpath = "//span[text()='General Information']")
    public WebElement generalInfo;

    public Integer getPageNumber() {
        return Integer.valueOf(pageNumber.getAttribute("value"));
    }

    public void clickToCreateACar() {
        browserUtils.waitForStaleElement(createACarBtn);
        createACarBtn.click();
    }

    public void enterLicensePlate(String value) {
        licensePlate.clear();
        licensePlate.sendKeys(value);
    }

    public void enterDriver(String value) {
        //if we will do negative testing
        //and before there will be invalid text
        //we want to clear first
        //some applications have placeholders that might prevent correct text input
        driverInput.clear();
        driverInput.sendKeys(value);
    }

    public void enterLocation(String value) {
        location.clear();
        location.sendKeys(value);
    }

    public void enterModelYear(String value) {
        modelYear.clear();
        modelYear.sendKeys(value);
    }

    public void enterColor(String value) {
        color.clear();
        color.sendKeys(value);
    }

    public void enterPower(String value) {
        power.clear();
        power.sendKeys(value);
    }

    public void clickSaveAndClose() {
        saveAndClose.click();
    }

    public boolean verifyGeneralInformationIsDisplayed() {
        try {
            return generalInfo.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
