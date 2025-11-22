package pages;

import com.codeborne.selenide.Selenide;

public class FormsPage extends BasePage {

    @Override
    public FormsPage open() {
        Selenide.open(BASE_URL + "web#forms");
        return this;
    }

    @Override
    public BasePage isPageOpened() {
        return null;
    }
}
