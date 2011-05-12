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

    private WebDriver webDriver;
    private final WebDriverProvider driverProvider;

    public WebDriverPage(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
        this.webDriver = new LazyWebDriver(driverProvider);
    }

    public void get(String url) {
        makeNonLazy();
        webDriver().get(url);
    }

    public String getCurrentUrl() {
        makeNonLazy();
        return webDriver().getCurrentUrl();
    }

    public String getTitle() {
        makeNonLazy();
        return webDriver().getTitle();
    }

    public List<WebElement> findElements(By by) {
        makeNonLazy();
        return webDriver().findElements(by);
    }

    public WebElement findElement(By by) {
        makeNonLazy();
        return webDriver().findElement(by);
    }

    public String getPageSource() {
        makeNonLazy();
        return webDriver().getPageSource();
    }

    public void close() {
        makeNonLazy();
        webDriver().close();
    }

    public void quit() {
        makeNonLazy();
        webDriver().quit();
    }

    public Set<String> getWindowHandles() {
        makeNonLazy();
        return webDriver().getWindowHandles();
    }

    public String getWindowHandle() {
        makeNonLazy();
        return webDriver().getWindowHandle();
    }

    public TargetLocator switchTo() {
        makeNonLazy();
        return webDriver().switchTo();
    }

    public Navigation navigate() {
        makeNonLazy();
        return webDriver().navigate();
    }

    public Options manage() {
        makeNonLazy();
        return webDriver().manage();
    }

    private synchronized void makeNonLazy() {
        // keep doing this per call as WebDriver instances changes per thread.
        webDriver = driverProvider.get();
    }

    protected WebDriver webDriver() {
        return webDriver;
    }

}
