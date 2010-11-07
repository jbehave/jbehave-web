package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Provides WebDriver instances based on system property "browser".
 * WebDrivers are created based on the following browser values:
 * <ul>
 * <li>"firefox": {@link FirefoxDriver}</li>
 * <li>"ie": {@link InternetExplorerDriver}</li>
 * <li>"chrome": {@link ChromeDriver}</li>
 * <li>"html": {@link HtmlUnitDriver}</li>
 * </ul>
 * Browser property values are case-insensitive and defaults to "firefox" if
 * no "browser" system property is found.
 */
public class PropertyWebDriverProvider extends DelegatingWebDriverProvider {

    public enum Browser {
        FIREFOX, IE, CHROME, HTML
    }

    public void initialize() {
        delegate = createDriver(detectBrowser());
    }

    protected Browser detectBrowser() {
        return Browser.valueOf(Browser.class, System.getProperty("browser", "firefox").toUpperCase());
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
        case HTML:
            return new HtmlUnitDriver();
        }
    }

}