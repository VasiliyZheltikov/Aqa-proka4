package wrappers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import org.openqa.selenium.WebElement;

public class Picklist {

    WebElement webElement;

    public Picklist(WebElement webElement) {
        this.webElement = webElement;
    }


    public void select(String option) {
        $(webElement).click(); // открытые выпадающего списка
        $x(String.format("//option[contains(text(), '%s')]", option)).click();
    }
}
