package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

public interface WebDriverFactory {
    
    WebDriver get();

    void initialize();
    
}
