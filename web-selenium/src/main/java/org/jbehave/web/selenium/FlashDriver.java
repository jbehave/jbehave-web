package org.jbehave.web.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * <p>A {@link WebDriver} decorator that allows interaction with a Flash object via Javascript calls.
 * The WebDriver must be an instance of {@link JavascriptExecutor}.</p>
 * 
 * <p>The Flash object is found via the object ID provided.</p>
 */
public class FlashDriver implements WebDriver {

    protected WebDriver delegate;
    protected String flashObjectId;

    public FlashDriver(WebDriver delegate, String flashObjectId) {
        this.delegate = delegate;
        this.flashObjectId = flashObjectId;
    }

    public Object call(String functionName, String... args) {
        if (delegate instanceof JavascriptExecutor) {
            WebElement flashObject = findFlashObject(flashObjectId);
            String script = formatJavascript(functionName, args);
            return ((JavascriptExecutor) delegate).executeScript(script, flashObject);
        }
        throw new JavascriptNotSupported(delegate);
    }

    protected WebElement findFlashObject(String flashObjectId) {
        return delegate.findElement(By.id(flashObjectId));
    }

    protected String formatJavascript(String functionName, String... args) {
        List<String> quotedArgs = new ArrayList<String>();
        for (String arg : args) {
            quotedArgs.add("'" + arg + "'");
        }
        return "return arguments[0]." + functionName + "(" + join(quotedArgs, ",") + ");";
    }

    // Utility methods that map to SWF function calls
    public void click() {
        call("click");
    }

    public int percentLoaded() {
        return Integer.parseInt(call("PercentLoaded").toString());
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
    
    @SuppressWarnings("serial")
    public static class JavascriptNotSupported extends RuntimeException {

        public JavascriptNotSupported(WebDriver delegate) {
            super("Javascript not supported by WebDriver "+delegate);
        }
        
    }
}
