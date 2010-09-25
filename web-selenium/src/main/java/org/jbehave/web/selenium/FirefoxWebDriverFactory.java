package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxWebDriverFactory extends WebDriverFactory.AbstractDriverFactory {

    public void initialize() {
        delegate = new FirefoxDriver();
    }
}
