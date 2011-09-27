package org.jbehave.web.selenium;


import org.openqa.selenium.JavascriptExecutor;

public class SauceLabsContextView implements ContextView {

    private WebDriverProvider webDriverProvider;

    public SauceLabsContextView(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    public void show(String message) {
        sendContextMessage(message);
    }

    private void sendContextMessage(String message) {
        try {
            JavascriptExecutor je = (JavascriptExecutor) webDriverProvider.get();
            je.executeScript("sauce:context:" + message);
        } catch (Exception e) {
            // fail silently.
        }
    }

    public void close() {
        sendContextMessage("JBehave closing ContextView");
    }
}
