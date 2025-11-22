package wrappers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import org.openqa.selenium.WebElement;

public class Picklist {

    WebElement webElement;

    public Picklist(WebElement webElement) {
        this.webElement = webElement;
    }
    public void select(int optionNum) {
        $(webElement).click(); // открытые выпадающего списка
        $x(String.format("//select[@id='country']/option[%s]", optionNum)).click();
    }
}
