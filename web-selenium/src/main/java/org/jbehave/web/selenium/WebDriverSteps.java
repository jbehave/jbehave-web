package org.jbehave.web.selenium;

public class WebDriverSteps {

    protected WebDriverFactory driverFactory;

    public WebDriverSteps(WebDriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public WebDriverFactory getDriverFactory() {
        return driverFactory;
    }

}
