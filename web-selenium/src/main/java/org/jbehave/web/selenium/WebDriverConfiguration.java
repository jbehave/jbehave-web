package org.jbehave.web.selenium;

import org.jbehave.core.configuration.Configuration;

public class WebDriverConfiguration extends Configuration {

    private WebDriverFactory driverFactory;
    private WebDriverContext webDriverContext = new WebDriverContext();

    public WebDriverConfiguration() {
    }

    public WebDriverFactory webDriverFactory() {
        return driverFactory;
    }

    public WebDriverConfiguration useWebDriverFactory(WebDriverFactory webDriver){
        this.driverFactory = webDriver;
        return this;
    }

    public WebDriverContext webDriverContext() {
        return webDriverContext;
    }

    public WebDriverConfiguration useWebDriverContext(WebDriverContext webDriverContext) {
        this.webDriverContext = webDriverContext;
        return this;
    }

}
