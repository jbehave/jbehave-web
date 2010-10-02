package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Provides WebDriver instances as needed.
 * These can be per-story, per-scenario, or one for all stories.
 */
public class DefaultWebDriverProvider extends DelegatingWebDriverProvider {

    private Class<? extends WebDriver> type;

    /**
     * Provides instances of a supplied type
     * @param type the WebDriver type to instantiate.
     */
    public DefaultWebDriverProvider(Class<? extends WebDriver> type) {
        this.type = type;
    }

    /**
     * Provides instances of {@link FirefoxDriver}s.
     */
    public DefaultWebDriverProvider() {
        this.type = FirefoxDriver.class;
    }

    public void initialize() {
        try {
            delegate = type.newInstance();
        } catch (InstantiationException e) {
            new UnsupportedOperationException(e);
        } catch (IllegalAccessException e) {
            new UnsupportedOperationException(e);
        }
    }
}