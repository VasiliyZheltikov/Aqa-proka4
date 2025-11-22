package pages;

public abstract class BasePage {

    protected final String BASE_URL = "https://aqa-proka4.org/sandbox/";

    public BasePage() {
    }

    public abstract BasePage open();

    public abstract BasePage isPageOpened();
}
