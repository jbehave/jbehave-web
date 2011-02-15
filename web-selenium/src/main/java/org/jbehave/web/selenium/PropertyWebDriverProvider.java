package org.jbehave.web.selenium;

import java.net.MalformedURLException;
import java.util.Locale;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import static java.lang.Boolean.parseBoolean;

/**
 * Provides WebDriver instances based on system property "browser":
 * <ul>
 * <li>"android": {@link AndroidDriver}</li>
 * <li>"chrome": {@link ChromeDriver}</li>
 * <li>"firefox": {@link FirefoxDriver}</li>
 * <li>"htmlunit": {@link HtmlUnitDriver}</li>
 * <li>"ie": {@link InternetExplorerDriver}</li>
 * </ul>
 * Property values are case-insensitive and defaults to "firefox" if no
 * "browser" system property is found.
 * <p>
 * The drivers also accept the following properties:
 * <ul>
 * <li>"android": "webdriver.android.url" and
 * "webdriver.android.screenOrientation", defaulting to
 * "http://localhost:8080/hub" and "portrait".</li>
 * <li>"htmlunit": "webdriver.htmlunit.javascriptEnabled", defaulting to "true".
 * </li>
 * </ul>
 */
public class PropertyWebDriverProvider extends DelegatingWebDriverProvider {

    public enum Browser {
        ANDROID, CHROME, FIREFOX, HTMLUNIT, IE
    }

    public void initialize() {
        Browser browser = Browser.valueOf(Browser.class, System.getProperty("browser", "firefox").toUpperCase(usingLocale()));
        delegate = createDriver(browser);
    }

    private WebDriver createDriver(Browser browser) {
        switch (browser) {
        case ANDROID:
            return createAndroidDriver();
        case CHROME:
            return createChromeDriver();
        case FIREFOX:
            return createFirefoxDriver();
        case HTMLUNIT:
        default:
            return createHtmlUnitDriver();
        case IE:
            return createInternetExplorerDriver();
        }
    }

    protected WebDriver createAndroidDriver() {
        try {
            String url = System.getProperty("webdriver.android.url", "http://localhost:8080/hub");
            ScreenOrientation screenOrientation = ScreenOrientation.valueOf(System.getProperty(
                    "webdriver.android.screenOrientation", "portrait").toUpperCase(usingLocale()));
            AndroidDriver driver = new AndroidDriver(url);
            driver.rotate(screenOrientation);
            return driver;
        } catch (MalformedURLException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    protected ChromeDriver createChromeDriver() {
        return new ChromeDriver();
    }

    protected FirefoxDriver createFirefoxDriver() {
        return new FirefoxDriver();
    }

    protected WebDriver createHtmlUnitDriver() {
        HtmlUnitDriver driver = new HtmlUnitDriver();
        boolean javascriptEnabled = parseBoolean(System.getProperty("webdriver.htmlunit.javascriptEnabled", "true"));
        driver.setJavascriptEnabled(javascriptEnabled);
        return driver;
    }

    protected InternetExplorerDriver createInternetExplorerDriver() {
        return new InternetExplorerDriver();
    }
    
    protected Locale usingLocale() {
        return Locale.getDefault();
    }

}