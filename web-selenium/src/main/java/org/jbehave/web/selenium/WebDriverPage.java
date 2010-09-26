package org.jbehave.web.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

/**
 * Abstract base class for pages that use the WebDriver API. It contains common
 * page methods, with a view to implement the <a
 * href="http://code.google.com/p/selenium/wiki/PageObjects">Page Objects</a>
 * pattern.
 */
public abstract class WebDriverPage implements WebDriver {

    private final WebDriverFactory driverFactory;

    public WebDriverPage(WebDriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public void get(String s) {
        driverFactory.get().get(s);
    }

    public String getCurrentUrl() {
        return driverFactory.get().getCurrentUrl();
    }

    public String getTitle() {
        return driverFactory.get().getTitle();
    }

    public List<WebElement> findElements(By by) {
        return driverFactory.get().findElements(by);
    }

    public WebElement findElement(By by) {
        return driverFactory.get().findElement(by);
    }

    public String getPageSource() {
        return driverFactory.get().getPageSource();
    }

    public void close() {
        driverFactory.get().close();
    }

    public void quit() {
        driverFactory.get().quit();
    }

    public Set<String> getWindowHandles() {
        return driverFactory.get().getWindowHandles();
    }

    public String getWindowHandle() {
        return driverFactory.get().getCurrentUrl();
    }

    public TargetLocator switchTo() {
        return driverFactory.get().switchTo();
    }

    public Navigation navigate() {
        return driverFactory.get().navigate();
    }

    public Options manage() {
        return driverFactory.get().manage();
    }
}
