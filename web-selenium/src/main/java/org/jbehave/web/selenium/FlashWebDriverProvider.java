package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Provides FlashDriver instances, using the Flash object ID provided.
 */
public class FlashWebDriverProvider extends DelegatingWebDriverProvider {

    private String flashObjectId;
    
    public FlashWebDriverProvider(String flashObjectId) {
        this.flashObjectId = flashObjectId;
    }

    public void initialize() {
        delegate.set(new FlashDriver(new FirefoxDriver(), flashObjectId));
    }

}