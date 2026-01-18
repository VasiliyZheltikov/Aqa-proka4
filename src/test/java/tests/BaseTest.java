package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.TestListener;

import static com.codeborne.selenide.Selenide.closeWebDriver;

@Listeners(TestListener.class)
public class BaseTest {

    SoftAssert softAssert;
    FormsPage formsPage;

    @BeforeMethod(alwaysRun = true, description = "Настройка драйвера")
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
        options.addArguments("--headless");
        Configuration.browserCapabilities = options;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
            .screenshots(true)
            .savePageSource(true));

        softAssert = new SoftAssert();
        formsPage = new FormsPage();
    }

    @AfterMethod(alwaysRun = true, description = "Закрытие браузера")
    public void tearDown(ITestResult result) {
        if(ITestResult.FAILURE == result.getStatus()){
            byte[] screen = Selenide.screenshot(OutputType.BYTES);
            saveScreen("Screen",screen);
        }
        if(WebDriverRunner.getWebDriver()!=null) {
            closeWebDriver();
        }
    }
    //Создание скринов
    @Attachment(value = "{name}", type = "image/png")
    private static byte[] saveScreen(String name,byte[] image){
        return image;
    }
}
