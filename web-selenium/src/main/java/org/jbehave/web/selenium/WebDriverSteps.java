package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

public class WebDriverSteps {

    protected WebDriver driver;

    public WebDriverSteps(WebDriver driver) {
        this.driver = driver;
    }

    public void setDriver(WebDriver driver) {
        ((WebDriverProxy) this.driver).setProxy(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }
}
