package org.jbehave.web.selenium;

/**
 * Base steps class that can be used in scenarios that use the WebDriver API.
 */
public class WebDriverSteps {

    protected WebDriverFactory driverFactory;

    public WebDriverSteps(WebDriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public WebDriverFactory getDriverFactory() {
        return driverFactory;
    }

}
