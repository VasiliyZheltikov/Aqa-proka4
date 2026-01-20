package pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import wrappers.Button;
import wrappers.Checkboxes;
import wrappers.Inputs;
import wrappers.Picklist;

@Log4j2
public class FormsPage extends BasePage {

    @Override
    @Step("Открытие страницы с веб-формами")
    public FormsPage open() {
        log.info("Открытие страницы {}", BASE_URL);
        Selenide.open(BASE_URL);
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

    @Step("Заполнение поля {labelName} в форме {formName}")
    public FormsPage fillInputFieldWithValue(String formName, String labelName, String inputValue) {
        log.info("Заполнение поля {}...", labelName);
        new Inputs(formName, labelName).write(inputValue);
        log.info("Поле {} заполнено значением: {}", labelName, inputValue);
        return this;
    }

    @Step("Выбор значения выпадающего списка {labelName} в форме {formName}")
    public FormsPage chooseOptionFromList(String formName, String labelName, String option) {
        log.info("Выбор значения в поле {}...", labelName);
        new Picklist(formName, labelName).select(option);
        log.info("Для выпадающего списка {} выбрано значение: {}", labelName, option);
        return this;
    }

    @Step("Активация чекбокса {labelText} в форме {formName}")
    public FormsPage activateCheckbox(String formName, String labelText) {
        log.info("Активация чекбокса {}...", labelText);
        new Checkboxes(formName, labelText).activate();
        log.info("Чекбокс {} активен", labelText);
        return this;
    }

    @Step("Нажатие кнопки {buttonName} в форме {formName}")
    public void pushToButton(String formName, String buttonName) {
        log.info("Нажатие кнопки {}...", buttonName);
        new Button(formName, buttonName).clickButton();
        log.info("Кнопка {} нажата", buttonName);
    }

    @Step("Проверка отображения сообщения об ошибке под полем {labelName} в форме {formName}")
    public boolean isValidationMessageDisplayed(String formName, String labelName) {
        return new Inputs(formName, labelName).findError().isDisplayed();
    }

    @Step("Проверка текста сообщения об ошибке под полем {labelName} в форме {formName}")
    public String getValidationMessageText(String formName, String labelName) {
        return new Inputs(formName, labelName).findError().getText();
    }

    @Step("Проверка отображения уведомления о статусе отправке формы")
    public boolean isFormResultDisplayed(String resultMessage) {
        return $(withText(resultMessage)).isDisplayed();
    }
}
