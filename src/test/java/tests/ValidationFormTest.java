package tests;

import dto.PersonData;
import dto.PersonFactory;
import jdk.jfr.Description;
import org.testng.annotations.Test;

public class ValidationFormTest extends BaseTest {

    @Test(
        testName = "Проверка успешной отправки формы",
        description = "Проверка успешной отправки формы",
        groups = {"smoke"}
    )
    @Description("Проверка успешной отправки формы")
    public void sendFormWithValidatedFilledFields() {
        PersonData personData = PersonFactory.getPersonData();
        formWithValidationPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                personData.getUsername(),
                personData.getEmail(),
                personData.getPassword()
            );
        softAssert.assertTrue(formWithValidationPage.isFormResultDisplayed(),
            "Результат отправки формы не отобразился");
        softAssert.assertEquals(formWithValidationPage.getResultMessage(),
            "Все проверки пройдены! Форма валидна.",
            "Неверный текст сообщения об успешной отправке формы");
        softAssert.assertAll();
    }
}
