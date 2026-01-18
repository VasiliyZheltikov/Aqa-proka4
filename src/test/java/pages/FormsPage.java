package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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
    private final WebElement REGISTER_BUTTON = $(byId("submitBtn"));
    private static final WebElement USERNAME = $(byId("username"));
    private static final WebElement EMAIL = $(byId("email"));
    private static final WebElement PASSWORD = $(byId("password"));
    private static final WebElement COUNTRY = $(byId("country"));
    private static final WebElement AGREEMENT_CHECKBOX = $(byId("terms"));

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

    public FormsPage formResultIsNotDisplaying() {
        $(byId("formResult")).shouldNotBe(visible);
        log.info("Результат отправки формы не отображается");
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
        log.info("Заполнение поля Username...");
        new Inputs(USERNAME).write(username);
        log.info("Поле Username заполнено значением: {}", username);
        log.info("Заполнение поля Email...");
        new Inputs(EMAIL).write(email);
        log.info("Поле Email заполнено значением: {}", email);
        log.info("Заполнение поля Password...");
        new Inputs(PASSWORD).write(password);
        log.info("Поле Password заполнено значением: {}", password);
        log.info("Проверка флага isCountrySelected...");
        log.info("Состояние флага isCountrySelected: {}", isCountrySelected);
        if (isCountrySelected) {
            log.info("Выбор страны из выпадающего списка...");
            new Picklist(COUNTRY).select(country);
            log.info("Выбрана страна: {}", country);
        }
        log.info("Проверка флага isCheckboxChecked...");
        log.info("Состояние флага isCheckboxChecked: {}", isCheckboxChecked);
        if (isCheckboxChecked) {
            new Checkboxes(AGREEMENT_CHECKBOX).activateCheckbox();
            log.info("Чекбокс отмечен");
        }
        log.info("Нажатие на кнопку отправки формы...");
        new Button(REGISTER_BUTTON).clickButton();
        log.info("Кнопка отправки формы нажата");
        return this;
    }
}
