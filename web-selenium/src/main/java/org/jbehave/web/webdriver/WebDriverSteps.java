package org.jbehave.web.webdriver;

public class WebDriverSteps {

    protected WebDriverFactory driverFactory;

    public WebDriverSteps(WebDriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public WebDriverFactory getDriverFactory() {
        return driverFactory;
    }

}
