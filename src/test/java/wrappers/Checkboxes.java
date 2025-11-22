package wrappers;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.WebElement;

public class Checkboxes {

    WebElement webElement;

    public Checkboxes(WebElement webElement) {
        this.webElement = webElement;
    }

    public void activateCheckbox() {
        $(webElement).click();
    }

}
