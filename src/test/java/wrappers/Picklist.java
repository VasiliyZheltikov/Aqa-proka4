package wrappers;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.WebElement;

public class Picklist {

    WebElement webElement;

    public Picklist(WebElement webElement) {
        this.webElement = webElement;
    }

    public void select(String valueText) {
        $(webElement).click(); // открытые выпадающего списка
        $(byText(valueText)).click();
    }
}
