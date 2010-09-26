package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Delegating abstract factory that provides {@link WebDriver}s specified by
 * concrete implementations.
 */
public abstract class DelegatingWebDriverFactory implements WebDriverFactory {

    protected WebDriver delegate;

    public WebDriver get() {
        return delegate;
    }

}