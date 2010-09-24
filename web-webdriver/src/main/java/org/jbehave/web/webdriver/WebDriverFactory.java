package org.jbehave.web.webdriver;

import org.openqa.selenium.WebDriver;

public interface WebDriverFactory {
    
    WebDriver get();

    void initialize();

    public abstract class AbstractDriverFactory {

        protected WebDriver delegate;

        public WebDriver get() {
            return delegate;
        }
    }
}
