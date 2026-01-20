package wrappers;

import static com.codeborne.selenide.Selenide.$x;

public class Button {

    String formName;

    public Button(String formName) {
        this.formName = formName;
    }

    public void clickButtonOnFormByButtonName(String buttonName) {
        $x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]"
                + "//button[contains(normalize-space(), '%s')]",
            formName,
            buttonName))
            .click();
    }

    public void clickButtonOnFormByFieldLabelName(String fieldLabelName) {
        $x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]"
                + "//label[contains(text(), '%s')]//ancestor::div[2]//input//parent::div//button",
            formName,
            fieldLabelName))
            .click();
    }
}
