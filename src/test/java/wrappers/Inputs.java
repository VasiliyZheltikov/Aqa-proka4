package wrappers;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.WebElement;

public class Inputs {

    WebElement webElement;

    public Inputs(WebElement webElement) {
        this.webElement = webElement;
    }

    public void write(String text) {
        $(webElement).val(text);
    }
}
