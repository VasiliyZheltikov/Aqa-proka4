package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import wrappers.Button;
import wrappers.Inputs;

@Log4j2
public class FormWithValidationPage extends BasePage {

    private final SelenideElement LABEL = $(byText("WEB Sandbox"));
    private final WebElement VALIDATE_AND_SEND_BUTTON = $(byId("valSubmitBtn"));
    private static final WebElement USERNAME = $(byId("val-username"));
    private static final WebElement EMAIL = $(byId("val-email"));
    private static final WebElement PASSWORD = $(byId("val-password"));
    private static final WebElement CONFIRM_PASSWORD = $(byId("val-confirm-password"));

    @Override
    @Step("Открытие страницы с веб-формами")
    public FormWithValidationPage open() {
        log.info("Открытие страницы {}web#forms", BASE_URL);
        Selenide.open(BASE_URL + "web#forms");
        return this;
    }

    @Override
    @Step("Проверка открытия страницы с веб-формами")
    public FormWithValidationPage isPageOpened() {
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
    public FormWithValidationPage fillRegistrationForm(
        String username,
        String email,
        String password,
        String confirmPassword
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
        log.info("Заполнение поля Подтвердите Password...");
        new Inputs(CONFIRM_PASSWORD).write(confirmPassword);
        log.info("Поле Подтвердите Password заполнено значением: {}", confirmPassword);
        log.info("Нажатие на кнопку отправки формы...");
        new Button(VALIDATE_AND_SEND_BUTTON).clickButton();
        log.info("Кнопка отправки формы нажата");
        return this;
    }

    @Step("Проверка отображения уведомления об успешной отправке формы")
    public boolean isFormResultDisplayed() {
        return $(byId("valFormResult")).isDisplayed();
    }

    @Step("Получение текста результата отправки формы")
    public String getResultMessage() {
        return $(byId("valFormResult")).getText();
    }

    @Step("Проверка отображения ошибки под заполняемым полем")
    public boolean isErrorFieldMessageDisplayed(String id) {
        return $(byId(id)).isDisplayed();
    }

    @Step("Получение текста ошибки под заполняемым полем")
    public String getErrorFieldMessage(String id) {
        return $(byId(id)).getText();
    }
}
