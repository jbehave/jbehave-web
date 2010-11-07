package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

public interface WebDriverProvider {
    
    /**
     * Get the WebDriver instance
     */
    WebDriver get();

    /**
     * Make a new browser frame for the WebDriver implementation in question.
     */
    void initialize();

    /**
     * If the WebDriver instance implements TakesScreenshot, then calling this will
     * take a screen shot to the path in question. Otherwise it'll be ignored.
     * @param path the save-as path
     */
    void saveScreenshotTo(String path);

    /**
     * Close browser frame
     */
    void quit();
}
