package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import pages.FormsPage;

public class BaseTest {

    SoftAssert softAssert;
    FormsPage formsPage;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        Configuration.screenshots = true;
        Configuration.savePageSource = true;
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", chromePrefs);
        // options.addArguments("--incognito"); // в этом режиме ругается на незащищённое подключение
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
            .screenshots(true)
            .savePageSource(true));

        softAssert = new SoftAssert();
        formsPage = new FormsPage();

    }
}
