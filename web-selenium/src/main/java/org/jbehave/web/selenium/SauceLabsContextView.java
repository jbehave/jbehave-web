package org.jbehave.web.selenium;


import org.openqa.selenium.JavascriptExecutor;

public class SauceLabsContextView implements ContextView {

    private WebDriverProvider webDriverProvider;

    public SauceLabsContextView(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    public void show(String scenario, String step) {
        sendContextMessage(step);
    }

    private void sendContextMessage(String step) {
        try {
            JavascriptExecutor je = (JavascriptExecutor) webDriverProvider.get();
            je.executeScript("sauce:context=Step: " + step);
        } catch (Exception e) {
        }
    }

    public void close() {
    }
}
