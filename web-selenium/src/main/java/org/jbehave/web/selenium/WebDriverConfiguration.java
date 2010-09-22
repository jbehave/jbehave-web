package org.jbehave.web.selenium;

import org.jbehave.core.configuration.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverConfiguration extends Configuration {

    private WebDriver driver = defaultWebDriver();
    private WebDriverContext webDriverContext = new WebDriverContext();

    public WebDriverConfiguration() {
    }

    public WebDriver webDriver() {
        return driver;
    }

    public WebDriverConfiguration useWebDriver(WebDriver webDriver){
        this.driver = webDriver;
        return this;
    }

    public WebDriverContext webDriverContext() {
        return webDriverContext;
    }

    public WebDriverConfiguration useWebDriverContext(WebDriverContext webDriverContext) {
        this.webDriverContext = webDriverContext;
        return this;
    }

    public static WebDriver defaultWebDriver() {
        
        return new FirefoxDriver();
    }


}
