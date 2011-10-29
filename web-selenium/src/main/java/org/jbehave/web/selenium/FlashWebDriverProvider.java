package org.jbehave.web.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * Provides FlashDriver instances, using the Flash object ID and the
 * Javascript-enabled WebDriver (i.e. an instance of {@link JavascriptExecutor})
 * provided.
 */
public class FlashWebDriverProvider extends DelegatingWebDriverProvider {

    private String flashObjectId;
    private WebDriver javascriptDriver;

    public FlashWebDriverProvider(String flashObjectId, WebDriver javascriptDriver) {
        this.flashObjectId = flashObjectId;
        this.javascriptDriver = javascriptDriver;
    }

    public void initialize() {
        delegate.set(new FlashDriver(javascriptDriver, flashObjectId));
    }

}