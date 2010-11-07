package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Provides FirefoxDriver instances.
 */
public class DefaultWebDriverProvider extends PropertyWebDriverProvider {

    public DefaultWebDriverProvider() {
    }

    @Override
    protected Browser detectBrowser() {
        return Browser.FIREFOX;
    }
}