package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Provides WebDriver instances of given type, instantiating it using the
 * default constructor.
 */
public class TypeWebDriverProvider extends DelegatingWebDriverProvider {

    private Class<? extends WebDriver> type;

    /**
     * Provides instances of {@link FirefoxDriver}s.
     */
    public TypeWebDriverProvider() {
        this(FirefoxDriver.class);
    }

    /**
     * Provides instances of a supplied type
     * 
     * @param type the WebDriver type to instantiate.
     */
    public TypeWebDriverProvider(Class<? extends WebDriver> type) {
        this.type = type;
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