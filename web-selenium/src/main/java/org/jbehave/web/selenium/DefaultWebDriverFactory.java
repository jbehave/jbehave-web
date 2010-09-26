package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Default factory that provides instances of {@link FirefoxDriver}s.
 */
public class DefaultWebDriverFactory extends DelegatingWebDriverFactory {

    public void initialize() {
        delegate = new FirefoxDriver();
    }
}
