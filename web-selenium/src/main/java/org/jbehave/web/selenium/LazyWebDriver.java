package org.jbehave.web.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class LazyWebDriver implements WebDriver {

    private WebDriverProvider wdp;

    public LazyWebDriver(WebDriverProvider wdp) {
        this.wdp = wdp;
    }

    public void get(String s) {
        wdp.get().get(s);
    }

    public String getCurrentUrl() {
        return wdp.get().getCurrentUrl();
    }

    public String getTitle() {
        return wdp.get().getTitle();
    }

    public List<WebElement> findElements(By by) {
        return wdp.get().findElements(by);
    }

    public WebElement findElement(By by) {
        return wdp.get().findElement(by);
    }

    public String getPageSource() {
        return wdp.get().getPageSource();
    }

    public void close() {
        wdp.get().close();
    }

    public void quit() {
        wdp.get().quit();
    }

    public Set<String> getWindowHandles() {
        return wdp.get().getWindowHandles();
    }

    public String getWindowHandle() {
        return wdp.get().getWindowHandle();
    }

    public TargetLocator switchTo() {
        return wdp.get().switchTo();
    }

    public Navigation navigate() {
        return wdp.get().navigate();
    }

    public Options manage() {
        return wdp.get().manage();
    }
}
