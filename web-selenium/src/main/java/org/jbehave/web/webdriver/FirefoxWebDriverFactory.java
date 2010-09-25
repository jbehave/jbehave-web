package org.jbehave.web.webdriver;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxWebDriverFactory extends WebDriverFactory.AbstractDriverFactory {

    public void initialize() {
        delegate = new FirefoxDriver();
    }
}
