package wrappers;

import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.WebElement;

public class Inputs {

    String formName;
    String label;

    public Inputs(String formName, String label) {
        this.formName = formName;
        this.label = label;
    }

    private WebElement findInputOnFormByLabel() {
        return $x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]"
                + "//label[contains(text(), '%s')]//ancestor::div[1]//input",
            formName,
            label));
    }

    public void write(String text) {
        $(findInputOnFormByLabel()).val(text);
    }

    public WebElement findError() {
        return $x(String.format("//*[contains(text(), '%s')]//ancestor::div[3]//"
                + "label[contains(text(), '%s')]//ancestor::div[1]//p",
            formName,
            label));
    }

    public int count() {
        return $$x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]"
                + "//label[contains(text(), '%s')]//ancestor::div[2]//input",
            formName,
            label))
            .size();
    }
}
