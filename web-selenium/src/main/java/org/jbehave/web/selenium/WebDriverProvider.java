package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

public interface WebDriverProvider {
    
    WebDriver get();

    void initialize();

    boolean saveScreenshotTo(String path);
    
}
