package tests;

import com.github.javafaker.Faker;
import dto.FormAttributes;
import dto.FormFactory;
import dto.PersonData;
import dto.PersonFactory;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Log4j2
public class FormsTests extends BaseTest {

    @DataProvider(name = "Negative inputs data for simple registration form")
    public Object[][] negativeDataForSimpleRegistrationForm() {
        return new Object[][]{
            {
                "", // without required Username
                PersonFactory.getPersonData().getEmail(),
                PersonFactory.getPersonData().getPassword(),
                PersonFactory.getPersonData().getCountry(),
                PersonFactory.getPersonData().isCountrySelected(),
                PersonFactory.getPersonData().isCheckboxChecked()
            },
            {
                PersonFactory.getPersonData().getUsername(),
                "", // without required Email
                PersonFactory.getPersonData().getPassword(),
                PersonFactory.getPersonData().getCountry(),
                PersonFactory.getPersonData().isCountrySelected(),
                PersonFactory.getPersonData().isCheckboxChecked()
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                "", // without required Password
                PersonFactory.getPersonData().getCountry(),
                PersonFactory.getPersonData().isCountrySelected(),
                PersonFactory.getPersonData().isCheckboxChecked()
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                PersonFactory.getPersonData().getPassword(),
                PersonFactory.getPersonData().getCountry(),
                false, // without required Country
                PersonFactory.getPersonData().isCheckboxChecked()
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                PersonFactory.getPersonData().getPassword(),
                PersonFactory.getPersonData().getCountry(),
                PersonFactory.getPersonData().isCountrySelected(),
                false // without required Checkbox
            }
        };
    }

    @DataProvider(name = "Negative inputs data for form with validation")
    public Object[][] negativeDataForFormWithValidation() {
        Faker faker = new Faker();
        FormAttributes formAttributes = FormFactory.getFormWithValidationAttributes();
        String password = PersonFactory.getPersonData().getPassword();
        String passwordOnlyLetters = faker.regexify("[а-яА-ЯёЁa-zA-Z]}{12}"); // 12 букв
        String passwordOnlyDigits = faker.regexify("[0-9]{12}"); // 12 любых цифр
        return new Object[][]{
            {
                faker.name().username().substring(0, 4), // 4 символа при ГЗ = 5 (ГЗ 5 проверяется в позитивном тесте)
                PersonFactory.getPersonData().getEmail(),
                password,
                password,
                formAttributes.getUsernameLabel(),
                "Username должен содержать минимум 5 символов"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getUsername(), // Заполнение поля Email значением без "@"
                password,
                password,
                formAttributes.getEmailLabel(),
                "Email должен содержать символ @"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                password.substring(0, 7), // 7 символов при ГЗ = 8 (ГЗ 8 проверяется в позитивном тесте)
                password.substring(0, 7),
                formAttributes.getPasswordLabel(),
                "Password должен содержать минимум 8 символов, включая буквы и цифры"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                passwordOnlyLetters, // 12 букв
                passwordOnlyLetters,
                formAttributes.getPasswordLabel(),
                "Password должен содержать минимум 8 символов, включая буквы и цифры"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                passwordOnlyDigits, // 12 любых цифр
                passwordOnlyDigits,
                formAttributes.getPasswordLabel(),
                "Password должен содержать минимум 8 символов, включая буквы и цифры"
            },
            {
                PersonFactory.getPersonData().getUsername(),
                PersonFactory.getPersonData().getEmail(),
                faker.internet().password(),
                faker.internet().password(), // Пароли не совпадают
                formAttributes.getConfirmPasswordLabel(),
                "Пароли не совпадают"
            }
        };
    }

    @DataProvider(name = "Adding and deleting fields in dynamic form")
    public Object[][] addingFieldsButtonsInDynamicForm() {
        FormAttributes formAttributes = FormFactory.getDynamicFormAttributes();
        return new Object[][]{
            {formAttributes.getAddEmailButtonName(), formAttributes.getEmailLabel()},
            {formAttributes.getAddPhoneButtonName(), formAttributes.getPhoneLabel()}
        };
    }

    @Test(
        testName = "Успешная отправка простой формы регистрации",
        description = "Успешная отправка простой формы регистрации",
        groups = {"smoke"}
    )
    @Feature("Простая форма регистрации")
    @Description("Проверка успешной отправки простой формы регистрации со всеми заполненными полями")
    public void registerWithFullFilledSimpleRegistrationForm() {
        FormAttributes form = FormFactory.getSimpleRegistrationFormAttributes();
        PersonData personData = PersonFactory.getPersonData();
        formsPage.open()
            .isPageOpened()
            .fillInputFieldWithValue(form.getFormName(), form.getUsernameLabel(), personData.getUsername())
            .fillInputFieldWithValue(form.getFormName(), form.getEmailLabel(), personData.getEmail())
            .fillInputFieldWithValue(form.getFormName(), form.getPasswordLabel(), personData.getPassword())
            .chooseOptionFromList(form.getFormName(), form.getCountryLabel(), personData.getCountry())
            .activateCheckbox(form.getFormName(), form.getCheckboxLabel())
            .pushToButton(form.getFormName(), form.getButtonName());
        Assert.assertTrue(formsPage.isFormResultDisplayed("Форма успешно отправлена!"),
            "Ошибка при отправке формы регистрации");
    }

    @Test(
        dataProvider = "Negative inputs data for simple registration form",
        testName = "Попытка отправки простой формы регистрации с пустым аттрибутом",
        description = "Попытка отправки простой формы регистрации с пустым аттрибутом",
        groups = {"negative"}
    )
    @Feature("Простая форма регистрации")
    @Description("Попытка отправки простой формы регистрации с пустым аттрибутом")
    public void tryToRegisterWithAnyEmptyAttributeInSimpleRegistrationForm(
        String username,
        String email,
        String password,
        String country,
        boolean chooseCountry,
        boolean activateCheckbox
    ) {
        FormAttributes form = FormFactory.getSimpleRegistrationFormAttributes();
        formsPage.open()
            .isPageOpened()
            .fillInputFieldWithValue(form.getFormName(), form.getUsernameLabel(), username)
            .fillInputFieldWithValue(form.getFormName(), form.getEmailLabel(), email)
            .fillInputFieldWithValue(form.getFormName(), form.getPasswordLabel(), password);
        if (chooseCountry) {
            formsPage.chooseOptionFromList(form.getFormName(), form.getCountryLabel(), country);
        }
        if (activateCheckbox) {
            formsPage.activateCheckbox(form.getFormName(), form.getCheckboxLabel());
        }
        formsPage.pushToButton(form.getFormName(), form.getButtonName());
        Assert.assertFalse(formsPage.isFormResultDisplayed("Форма успешно отправлена!"),
            "Форма регистрации успешно отправлена");
    }

    @Test(
        testName = "Успешная отправка формы регистрации с валидацией",
        description = "Успешная отправка формы регистрации с валидацией",
        groups = {"smoke"}
    )
    @Feature("Форма с валидацией")
    @Description("Проверка отправки формы с пройденной валидацией")
    public void registerWithFullValidatedFormWithValidation() {
        FormAttributes form = FormFactory.getFormWithValidationAttributes();
        PersonData personData = PersonFactory.getPersonData();
        formsPage.open()
            .isPageOpened()
            .fillInputFieldWithValue(form.getFormName(), form.getUsernameLabel(), personData.getUsername())
            .fillInputFieldWithValue(form.getFormName(), form.getEmailLabel(), personData.getEmail())
            .fillInputFieldWithValue(form.getFormName(), form.getPasswordLabel(), personData.getPassword())
            .fillInputFieldWithValue(form.getFormName(), form.getConfirmPasswordLabel(), personData.getPassword())
            .pushToButton(form.getFormName(), form.getButtonName());
        Assert.assertTrue(formsPage.isFormResultDisplayed("Все проверки пройдены! Форма валидна."),
            "Результат отправки формы не отобразился");
    }

    @Test(
        dataProvider = "Negative inputs data for form with validation",
        testName = "Проверка валидации полей и попытка отправить форму с валидацией",
        description = "Попытка отправки формы с валидацией с не пройденной валидацией по полю",
        groups = {"negative"}
    )
    @Feature("Форма с валидацией")
    @Description("Попытка отправки формы с валидацией с не пройденной валидацией по полю")
    public void tryToRegisterWithFailedValidationAtAnyFieldInValidationForm(
        String name,
        String email,
        String password,
        String confirmPassword,
        String notValidatedField,
        String fieldErrorText
    ) {
        FormAttributes form = FormFactory.getFormWithValidationAttributes();
        formsPage.open()
            .isPageOpened()
            .fillInputFieldWithValue(form.getFormName(), form.getUsernameLabel(), name)
            .fillInputFieldWithValue(form.getFormName(), form.getEmailLabel(), email)
            .fillInputFieldWithValue(form.getFormName(), form.getPasswordLabel(), password)
            .fillInputFieldWithValue(form.getFormName(), form.getConfirmPasswordLabel(), confirmPassword)
            .pushToButton(form.getFormName(), form.getButtonName());
        softAssert.assertTrue(
            formsPage.isFormResultDisplayed("Форма содержит ошибки. Исправьте их и попробуйте снова."),
            "Результат отправки формы не отобразился");
        softAssert.assertTrue(formsPage.isValidationMessageDisplayed(form.getFormName(), notValidatedField),
            "Не отобразился текст ошибки под непрошедшим валидацию полем");
        softAssert.assertEquals(formsPage.getValidationMessageText(form.getFormName(), notValidatedField),
            fieldErrorText,
            String.format("Текст сообщения об ошибке валидации в поле '%s' отличается", notValidatedField));
        softAssert.assertAll();
    }

    @Test(
        testName = "Добавление и удаление полей в динамической форме регистрации",
        description = "Добавление и удаление полей в динамической форме регистрации",
        dataProvider = "Adding and deleting fields in dynamic form"
    )
    @Feature("Динамическая форма регистрации")
    @Description("Добавление и удаление поля в динамической форме регистрации")
    public void addEmailFieldInDynamicForm(
        String addingFieldButtonName,
        String fieldLabel
    ) {
        FormAttributes form = FormFactory.getDynamicFormAttributes();
        formsPage.open()
            .isPageOpened()
            .pushToButton(form.getFormName(), addingFieldButtonName);
        softAssert.assertEquals(formsPage.countFieldsInForm(form.getFormName(), fieldLabel),
            2,
            String.format("Поле '%s' не добавлено", fieldLabel));
        formsPage.pushToDeleteInputButton(form.getFormName(),fieldLabel);
        softAssert.assertEquals(formsPage.countFieldsInForm(form.getFormName(),fieldLabel),
            1,
            String.format("Поле %s не удалено", fieldLabel));
    }
}