package wrappers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import org.openqa.selenium.WebElement;

public class Checkboxes {

    String formName;
    String label;

    public Checkboxes(String formName, String label) {
        this.formName = formName;
        this.label = label;
    }

    private WebElement findSelectOnFormByLabel() {
        return $x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]//span[contains(text(), '%s')]//ancestor::label//input",
            formName,
            label));
    }

    public void activate() {
        $(findSelectOnFormByLabel()).click();
    }

}
