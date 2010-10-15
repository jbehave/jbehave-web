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

    private final WebDriverProvider driverProvider;

    public WebDriverPage(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public void get(String url) {
        webDriver().get(url);
    }

    public String getCurrentUrl() {
        return webDriver().getCurrentUrl();
    }

    public String getTitle() {
        return webDriver().getTitle();
    }

    public List<WebElement> findElements(By by) {
        return webDriver().findElements(by);
    }

    public WebElement findElement(By by) {
        return webDriver().findElement(by);
    }

    public String getPageSource() {
        return webDriver().getPageSource();
    }

    public void close() {
        webDriver().close();
    }

    public void quit() {
        webDriver().quit();
    }

    public Set<String> getWindowHandles() {
        return webDriver().getWindowHandles();
    }

    public String getWindowHandle() {
        return webDriver().getWindowHandle();
    }

    public TargetLocator switchTo() {
        return webDriver().switchTo();
    }

    public Navigation navigate() {
        return webDriver().navigate();
    }

    public Options manage() {
        return webDriver().manage();
    }

    protected WebDriver webDriver() {
        return driverProvider.get();
    }

}
