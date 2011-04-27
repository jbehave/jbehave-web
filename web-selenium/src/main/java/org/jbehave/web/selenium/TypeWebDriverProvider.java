package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Provides WebDriver instances of given type, instantiating it using the
 * default constructor.  If not type is specified, FirefoxDriver is used.
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
            delegate.set(type.newInstance());
        } catch (InstantiationException e) {
            throw new UnsupportedOperationException(e);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}