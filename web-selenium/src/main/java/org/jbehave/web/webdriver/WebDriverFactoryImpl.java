package org.jbehave.web.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactoryImpl implements WebDriverFactory {

    private WebDriver delegate;

    public WebDriver get() {
        return delegate;
    }

    public void initialize() {
        delegate = new FirefoxDriver();
    }
}
