package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

/**
 * Delegating abstract implementation that provides {@link WebDriver}s specified by
 * the concrete delegate.
 */
public abstract class DelegatingWebDriverProvider implements WebDriverProvider {

    protected WebDriver delegate;

    public WebDriver get() {
        return delegate;
    }

}