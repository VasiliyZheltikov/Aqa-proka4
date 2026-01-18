package tests;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

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
        PersonData personData = PersonFactory.getPersonData();
        return new Object[][]{
            {
                "", // without required Username
                personData.getEmail(),
                personData.getPassword(),
                personData.getCountry(),
                personData.isCountrySelected(),
                personData.isCheckboxChecked()
            },

            {
                personData.getUsername(),
                "", // without required Email
                personData.getPassword(),
                personData.getCountry(),
                personData.isCountrySelected(),
                personData.isCheckboxChecked()
            },

            {
                personData.getUsername(),
                personData.getEmail(),
                "", // without required Password
                personData.getCountry(),
                personData.isCountrySelected(),
                personData.isCheckboxChecked()
            },
            {
                personData.getUsername(),
                personData.getEmail(),
                personData.getPassword(),
                personData.getCountry(),
                false, // without required Country
                personData.isCheckboxChecked()
            },

            {
                personData.getUsername(),
                personData.getEmail(),
                personData.getPassword(),
                personData.getCountry(),
                personData.isCountrySelected(),
                false // without required Checkbox
            },
        };
    }

    @Test
    @Description("Проверка успешной отправки формы")
    public void registerWithFullFilledForm() {
        PersonData personData = PersonFactory.getPersonData();
        formsPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                personData.getUsername(),
                personData.getEmail(),
                personData.getPassword(),
                personData.getCountry(),
                personData.isCountrySelected(),
                personData.isCheckboxChecked()
            );
        Assert.assertTrue($(byId("formResult")).isDisplayed(),
            "Ошибка при отправке формы регистрации");
    }

    @Test(dataProvider = "Negative inputs data for register form")
    @Description("Попытка отправки формы без заполнения каждого из обязательных полей")
    public void tryToRegisterWithSomeEmptyValueInForm(
        String username,
        String email,
        String password,
        String country,
        boolean isCountrySelected,
        boolean isCheckboxChecked) {
        formsPage.open()
            .isPageOpened()
            .fillRegistrationForm(
                username,
                email,
                password,
                country,
                isCountrySelected,
                isCheckboxChecked
            );
        Assert.assertFalse($(byId("formResult")).isDisplayed(),
            "Отобразился статус отправки формы"); // статус отправки формы не отобразился
    }
}
