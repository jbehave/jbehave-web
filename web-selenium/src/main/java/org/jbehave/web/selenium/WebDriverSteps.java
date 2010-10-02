package org.jbehave.web.selenium;

/**
 * Base steps class that can be used in scenarios that use the WebDriver API.
 */
public class WebDriverSteps {

    protected WebDriverProvider driverProvider;

    public WebDriverSteps(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public WebDriverProvider getDriverProvider() {
        return driverProvider;
    }

}
