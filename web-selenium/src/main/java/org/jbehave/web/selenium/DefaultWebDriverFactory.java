package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Makes WebDriver instances as needed.
 * These can be per-story, per-scenario, or one for all stories.
 */
public class DefaultWebDriverFactory extends DelegatingWebDriverFactory {

    private Class<? extends WebDriver> driver;

    /**
     * Provides instances of a supplied type
     * @param driver the WebDriver type to instantiate.
     */
    public DefaultWebDriverFactory(Class<? extends WebDriver> driver) {
        this.driver = driver;
    }

    /**
     * Provides instances of {@link FirefoxDriver}s.
     */
    public DefaultWebDriverFactory() {
        this.driver = FirefoxDriver.class;
    }

    public void initialize() {
        try {
            delegate = driver.newInstance();
        } catch (InstantiationException e) {
            new UnsupportedOperationException(e);
        } catch (IllegalAccessException e) {
            new UnsupportedOperationException(e);
        }
    }
}