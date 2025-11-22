package tests;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
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

    @Test
    public void register() {
        formsPage.open();
        new Inputs(USERNAME).write(faker.name().name());
        new Inputs(EMAIL).write(faker.internet().emailAddress());
        new Inputs(PASSWORD).write(faker.internet().password());
        new Picklist(COUNTRY).select("Russia");
        new Checkboxes(AGREEMENT_CHECKBOX).activateCheckbox();
        new Button(REGISTER_BUTTON).clickButton();
        Assert.assertEquals(FORM_RESULT.getText(),
            "Форма успешно отправлена!",
            "Ошибка при отправке формы регистрации");
    }
}
