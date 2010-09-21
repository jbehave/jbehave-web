package org.jbehave.web.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class WebDriverProxy implements WebDriver {

    private WebDriver proxy;

    public void setProxy(WebDriver proxy) {
        this.proxy = proxy;
    }

    public void get(String s) {
        proxy.get(s);
    }

    public String getCurrentUrl() {
        return proxy.getCurrentUrl();
    }

    public String getTitle() {
        return proxy.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return proxy.findElements(by);
    }

    public WebElement findElement(By by) {
        return proxy.findElement(by);
    }

    public String getPageSource() {
        return proxy.getPageSource();
    }

    public void close() {
        proxy.close();
    }

    public void quit() {
        proxy.quit();
    }

    public Set<String> getWindowHandles() {
        return proxy.getWindowHandles();
    }

    public String getWindowHandle() {
        return proxy.getCurrentUrl();
    }

    public TargetLocator switchTo() {
        return proxy.switchTo();
    }

    public Navigation navigate() {
        return proxy.navigate();
    }

    public Options manage() {
        return proxy.manage();
    }
}
