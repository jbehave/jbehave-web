package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Provides WebDriver instances based on system property "browser".
 */
public class PropertyWebDriverProvider extends DelegatingWebDriverProvider {

    public enum Browser {
        FIREFOX, IE, CHROME
    }

    public void initialize() {
        Browser browser = Browser.valueOf(Browser.class, System.getProperty("browser", "firefox").toUpperCase());
        delegate = createDriver(browser);
    }

    protected WebDriver createDriver(Browser browser) {
        switch (browser) {
        case FIREFOX:
        default:
            return new FirefoxDriver();
        case IE:
            return new InternetExplorerDriver();
        case CHROME:
            return new ChromeDriver();
        }
    }

}