package org.jbehave.web.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.apache.commons.lang.StringUtils.join;

public class FlashDriver implements WebDriver {

    private WebDriver delegate;
    private String flashObjectId;

    public FlashDriver(WebDriver driver, String flashObjectId) {
        this.delegate = driver;
        this.flashObjectId = flashObjectId;
    }

    public Object call(String functionName, String... args) {
        if (delegate instanceof JavascriptExecutor) {
            WebElement flashObject = delegate.findElement(By.id(flashObjectId));
            String script = formatJavascript(functionName, args);
            Object object = ((JavascriptExecutor) delegate).executeScript(script, flashObject);
            return object;
        }
        throw new RuntimeException("WebDriver not Javascript enabled: " + delegate);
    }

    private String formatJavascript(String functionName, String... args) {
        List<String> quotedArgs = new ArrayList<String>();
        for (String arg : args) {
            quotedArgs.add("'" + arg + "'");
        }
        return "return arguments[0]." + functionName + "(" + join(quotedArgs, ",") + ");";
    }

    public void click() {
        call("click");
    }

    public Object getVariable(String variableName) {
        return call("GetVariable", variableName);
    }

    public void gotoFrame(int frameNumber) {
        call("GotoFrame", Integer.toString(frameNumber));
    }

    public void pan(int x, int y, int mode) {
        call("Pan", Integer.toString(x), Integer.toString(y), Integer.toString(mode));
    }

    public int percentLoaded() {
        return new Integer(call("PercentLoaded").toString()).intValue();
    }

    // WebDriver methods
    public void get(String s) {
        delegate.get(s);
    }

    public String getCurrentUrl() {
        return delegate.getCurrentUrl();
    }

    public String getTitle() {
        return delegate.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return delegate.findElements(by);
    }

    public WebElement findElement(By by) {
        return delegate.findElement(by);
    }

    public String getPageSource() {
        return delegate.getPageSource();
    }

    public void close() {
        delegate.close();
    }

    public void quit() {
        delegate.quit();
    }

    public Set<String> getWindowHandles() {
        return delegate.getWindowHandles();
    }

    public String getWindowHandle() {
        return delegate.getWindowHandle();
    }

    public TargetLocator switchTo() {
        return delegate.switchTo();
    }

    public Navigation navigate() {
        return delegate.navigate();
    }

    public Options manage() {
        return delegate.manage();
    }
}
