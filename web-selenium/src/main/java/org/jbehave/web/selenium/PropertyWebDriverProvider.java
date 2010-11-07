package org.jbehave.web.selenium;

import java.net.MalformedURLException;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
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
 * <li>"android": {@link AndroidDriver}</li>
 * </ul>
 * Browser property values are case-insensitive and defaults to "firefox" if
 * no "browser" system property is found.
 * <p>Android driver also accepts properties "webdriver.android.url" and "webdriver.screen.orientation",
 * defaulting to "http://localhost:8080/hub" and "portrait".</li> 
 */
public class PropertyWebDriverProvider extends DelegatingWebDriverProvider {

    public enum Browser {
        FIREFOX, IE, CHROME, HTML, ANDROID
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
        case HTML:
            return new HtmlUnitDriver();
        case ANDROID:
            String url = System.getProperty("webdriver.android.url", "http://localhost:8080/hub");
            ScreenOrientation orientation = ScreenOrientation.valueOf(System.getProperty("webdriver.screen.orientation", "portrait").toUpperCase());
            try {
                AndroidDriver driver = new AndroidDriver(url);
                driver.rotate(orientation);
                return driver;
            } catch (MalformedURLException e) {
                throw new UnsupportedOperationException(e);
            }

        }
    }

}