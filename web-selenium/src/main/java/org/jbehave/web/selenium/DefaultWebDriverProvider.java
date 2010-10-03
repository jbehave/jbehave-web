package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Provides FirefoxDriver instances.
 */
public class DefaultWebDriverProvider extends TypeWebDriverProvider {

    /**
     * Provides instances of {@link FirefoxDriver}s.
     */
    public DefaultWebDriverProvider() {
        super(FirefoxDriver.class);
    }

}