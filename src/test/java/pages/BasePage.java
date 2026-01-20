package pages;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

public abstract class BasePage {

    protected final String BASE_URL = "https://aqa-proka4.org/sandbox/web";
    protected final SelenideElement LABEL = $(byText("WEB Sandbox"));

    public BasePage() {
    }

    public abstract BasePage open();

    public abstract BasePage isPageOpened();
}
