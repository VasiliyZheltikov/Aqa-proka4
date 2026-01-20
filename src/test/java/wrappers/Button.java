package wrappers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import org.openqa.selenium.WebElement;

public class Button {

    String formName;
    String buttonName;

    public Button(String formName, String buttonName) {
        this.formName = formName;
        this.buttonName = buttonName;
    }

    private WebElement findButtonOnFormByName() {
        return $x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]//button[contains(normalize-space(), '%s')]",
            formName,
            buttonName));
    }

    public void clickButton() {
        $(findButtonOnFormByName()).click();
    }

}
