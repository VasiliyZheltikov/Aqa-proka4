package tests;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

import com.github.javafaker.Faker;
import jdk.jfr.Description;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import wrappers.Button;
import wrappers.Checkboxes;
import wrappers.Inputs;
import wrappers.Picklist;

public class SimpleRegistrationFormTest extends BaseTest {

    private final WebElement USERNAME = $(byId("username"));
    private final WebElement EMAIL = $(byId("email"));
    private final WebElement PASSWORD = $(byId("password"));
    private final WebElement COUNTRY = $(byId("country"));
    private final WebElement AGREEMENT_CHECKBOX = $(byId("terms"));
    private final WebElement REGISTER_BUTTON = $(byId("submitBtn"));
    private final WebElement FORM_RESULT = $(byId("formResult"));

    Faker faker = new Faker();

    @DataProvider(name = "Inputs data for register form")
    public Object[][] inputsValues() {
        return new Object[][]{
            {"", faker.internet().emailAddress(), faker.internet().password(), USERNAME}, // without Username*
            {faker.name().name(), "", faker.internet().password(), EMAIL}, // without Email*
            {faker.name().name(), faker.internet().emailAddress(), "", PASSWORD}, // without Password*
        };
    }

    @Test
    @Description("Проверка успешной отправки формы")
    public void registerWithFullFilledForm() {
        formsPage.open();
        new Inputs(USERNAME).write(faker.name().name());
        new Inputs(EMAIL).write(faker.internet().emailAddress());
        new Inputs(PASSWORD).write(faker.internet().password());
        new Picklist(COUNTRY).select(2);
        new Checkboxes(AGREEMENT_CHECKBOX).activateCheckbox();
        new Button(REGISTER_BUTTON).clickButton();
        Assert.assertEquals(FORM_RESULT.getText(),
            "Форма успешно отправлена!",
            "Ошибка при отправке формы регистрации");
    }

    @Test(dataProvider = "Inputs data for register form")
    @Description("Попытка отправки формы без заполнения полей Username, Password, Email")
    public void registerWithSomeEmptyElement(String username, String email, String password, WebElement webElement) {
        formsPage.open();
        new Inputs(USERNAME).write(username);
        new Inputs(EMAIL).write(email);
        new Inputs(PASSWORD).write(password);
        new Picklist(COUNTRY).select(2);
        new Checkboxes(AGREEMENT_CHECKBOX).activateCheckbox();
        new Button(REGISTER_BUTTON).clickButton();
        softAssert.assertFalse(FORM_RESULT.isDisplayed(),
            "Отобразился статус отправки формы"); // статус отправки формы не отобразился
        softAssert.assertTrue(webElement.isEnabled(),
            "Незаполненное поле не выбрано"); // выбрано незаполненное поле
        softAssert.assertAll();
    }
}
