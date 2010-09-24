package org.jbehave.web.webdriver;

import org.openqa.selenium.WebDriver;

public class WebDriverSteps {

    protected WebDriverProxy driverProxy;

    public WebDriverSteps(WebDriverProxy driverProxy) {
        this.driverProxy = driverProxy;
    }

    public WebDriver getDriver() {
        return driverProxy;
    }

}
