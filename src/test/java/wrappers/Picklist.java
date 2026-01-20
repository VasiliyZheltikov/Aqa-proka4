package wrappers;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import org.openqa.selenium.WebElement;

public class Picklist {

    String formName;
    String label;

    public Picklist(String formName, String label) {
        this.formName = formName;
        this.label = label;
    }

    private WebElement findSelectOnFormByLabel() {
        return $x(String.format(
            "//*[contains(text(), '%s')]//ancestor::div[3]//label[contains(text(), '%s')]//ancestor::div[1]//select",
            formName,
            label));
    }

    public void select(String optionName) {
        $(findSelectOnFormByLabel()).click(); // открытые выпадающего списка
        $(withText(optionName)).click();
    }
}
