package tests;

import com.github.javafaker.Faker;
import dto.PersonData;
import dto.PersonFactory;
import jdk.jfr.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidationFormTest extends BaseTest {

    Faker faker = new Faker();

    @DataProvider(name = "Negative values for inputs")
    public Object[][] notValidatingInputsValues() {
        String password = PersonFactory.getPersonData().getPassword();
        String passwordOnlyLetters = faker.regexify("[а-яА-ЯёЁa-zA-Z]}{12}"); // 12 букв
        String passwordOnlyDigits = faker.regexify("[0-9]{12}"); // 12 любых цифр
        return new Object[][]{
            {
                faker.name().username().substring(0, 4), // 4 символа при ГЗ = 5 (ГЗ 5 проверяется в позитивном тесте)
                PersonFactory.getPersonData().getEmail(),
                password,
                password,
                "username-error",
                "Username должен содержать минимум 5 символов"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getUsername(), // Заполнение поля Email значением без "@"
                password,
                password,
                "email-error",
                "Email должен содержать символ @"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                password.substring(0, 7), // 7 символов при ГЗ = 8 (ГЗ 8 проверяется в позитивном тесте)
                password.substring(0, 7),
                "password-error",
                "Password должен содержать минимум 8 символов, включая буквы и цифры"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                passwordOnlyLetters, // 12 букв
                passwordOnlyLetters,
                "password-error",
                "Password должен содержать минимум 8 символов, включая буквы и цифры"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                passwordOnlyDigits, // 12 любых цифр
                passwordOnlyDigits,
                "password-error",
                "Password должен содержать минимум 8 символов, включая буквы и цифры"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                faker.internet().password(),
                faker.internet().password(), // Пароли не совпадают
                "confirm-password-error",
                "Пароли не совпадают"
            }
        };
    }

    @Test(
        testName = "Проверка успешной отправки формы",
        description = "Проверка успешной отправки формы",
        groups = {"smoke"}
    )
    @Description("Проверка успешного прохождения валидации и отправки формы")
    public void sendFormWithValidatedFilledFields() {
        PersonData personData = PersonFactory.getPersonData();
        formWithValidationPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                faker.name().username().substring(0, 5), // Проверка ГЗ = 5 символов
                personData.getEmail(),
                personData.getPassword().substring(0, 8), // Проверка ГЗ = 8 символов
                personData.getPassword().substring(0, 8)
            );
        softAssert.assertTrue(formWithValidationPage.isFormResultDisplayed(),
            "Результат отправки формы не отобразился");
        softAssert.assertEquals(formWithValidationPage.getResultMessage(),
            "Все проверки пройдены! Форма валидна.",
            "Неверный текст сообщения об успешной отправке формы");
        softAssert.assertAll();
    }

    @Test(
        dataProvider = "Negative values for inputs",
        testName = "Попытка отправки формы с не пройденной валидацией",
        description = "Проверка валидации полей и попытка отправить форму",
        groups = {"negative"}
    )
    @Description("Проверка неуспешного прохождения валидации")
    public void trySendFormWithNotValidatedFilledFields(
        String name,
        String email,
        String password,
        String confirmPassword,
        String id,
        String fieldErrorText
    ) {
        formWithValidationPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                name,
                email,
                password,
                confirmPassword
            );
        softAssert.assertTrue(formWithValidationPage.isFormResultDisplayed(),
            "Результат отправки формы не отобразился");
        softAssert.assertEquals(formWithValidationPage.getResultMessage(),
            "Форма содержит ошибки. Исправьте их и попробуйте снова.",
            "Неверный текст сообщения о неуспешных прохождении валидации и отправке формы");
        softAssert.assertTrue(formWithValidationPage.isErrorFieldMessageDisplayed(id),
            "Под не прошедшим валидацию полем не отобразился текст ошибки");
        softAssert.assertEquals(formWithValidationPage.getErrorFieldMessage(id),
            fieldErrorText,
            "Текст ошибки под не прошедшим валидацию полем не соответствует ожидаемому");
        softAssert.assertAll();
    }
}
