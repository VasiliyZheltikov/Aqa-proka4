package tests;

import dto.PersonData;
import dto.PersonFactory;
import jdk.jfr.Description;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Log4j2
public class SimpleRegistrationFormTest extends BaseTest {

    @DataProvider(name = "Negative inputs data for register form")
    public Object[][] inputsValues() {
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

    @Test(
        testName = "Проверка успешной отправки формы",
        description = "Проверка успешной отправки формы",
        groups = {"smoke"}
    )
    @Description("Проверка успешной отправки формы со всеми заполненными полями")
    public void registerWithFullFilledForm() {
        PersonData personData = PersonFactory.getPersonData();
        simpleRegistrationFormPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                personData.getUsername(),
                personData.getEmail(),
                personData.getPassword(),
                personData.getCountry(),
                personData.isCountrySelected(),
                personData.isCheckboxChecked()
            );
        Assert.assertTrue(simpleRegistrationFormPage.isFormResultDisplayed(),
            "Ошибка при отправке формы регистрации");
    }

    @Test(
        dataProvider = "Negative inputs data for register form",
        testName = "Попытка отправки формы без обязательного атрибута",
        description = "Попытка отправки формы без обязательного атрибута",
        groups = {"negative"}
    )
    @Description("Попытка отправки формы без заполнения каждого из обязательных полей")
    public void tryToRegisterWithSomeEmptyValueInForm(
        String username,
        String email,
        String password,
        String country,
        boolean isCountrySelected,
        boolean isCheckboxChecked) {
        simpleRegistrationFormPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                username,
                email,
                password,
                country,
                isCountrySelected,
                isCheckboxChecked
            );
        Assert.assertFalse(simpleRegistrationFormPage.isFormResultDisplayed(),
            "Отобразился статус отправки формы");
    }
}
