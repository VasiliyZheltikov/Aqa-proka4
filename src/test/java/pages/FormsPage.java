package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import wrappers.Button;
import wrappers.Checkboxes;
import wrappers.Inputs;
import wrappers.Picklist;

@Log4j2
public class FormsPage extends BasePage {

    private final SelenideElement LABEL = $(byText("WEB Sandbox"));
    private final WebElement USERNAME = $(byId("username"));
    private final WebElement EMAIL = $(byId("email"));
    private final WebElement PASSWORD = $(byId("password"));
    private final WebElement COUNTRY = $(byId("country"));
    private final WebElement AGREEMENT_CHECKBOX = $(byId("terms"));
    private final WebElement REGISTER_BUTTON = $(byId("submitBtn"));
    private final WebElement FORM_RESULT = $(byId("formResult"));

    Faker faker = new Faker();

    @Override
    @Step("Открытие страницы с веб-формами")
    public FormsPage open() {
        log.info("Открытие страницы {}web#forms", BASE_URL);
        Selenide.open(BASE_URL + "web#forms");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы с веб-формами")
    public FormsPage isPageOpened() {
        try {
            LABEL.shouldBe(visible);
            log.info("Открыта страница с веб-формами");
        } catch (Exception e) {
            log.error("Ошибка открытия страницы с веб-формами: {}", e.getMessage());
            Assert.fail("Ошибка открытия страницы с веб-формами: " + e.getMessage());
        }
        return this;
    }

    @Step("Заполнение формы регистрации")
    public FormsPage fillRegistrationForm(
        String username,
        String email,
        String password,
        String country,
        boolean isCountrySelected,
        boolean isCheckboxChecked
    ) {
        new Inputs(USERNAME).write(username);
        new Inputs(EMAIL).write(email);
        new Inputs(PASSWORD).write(password);
        if (isCountrySelected) {
            new Picklist(COUNTRY).select(country);
        }
        if (isCheckboxChecked) {
            new Checkboxes(AGREEMENT_CHECKBOX).activateCheckbox();
        }
        new Button(REGISTER_BUTTON).clickButton();
        return this;
    }
}
