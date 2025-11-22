package wrappers;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.WebElement;

public class Button {

    WebElement webElement;

    public Button(WebElement webElement) {
        this.webElement = webElement;
    }

    public void clickButton() {
        $(webElement).click();
    }

}
