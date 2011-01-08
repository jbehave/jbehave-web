package org.jbehave.web.selenium;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Delegating abstract implementation that provides {@link WebDriver}s specified
 * by the concrete delegate.
 */
public abstract class DelegatingWebDriverProvider implements WebDriverProvider {

    protected WebDriver delegate;

    public WebDriver get() {
        return delegate;
    }

    public boolean saveScreenshotTo(String path) {
        WebDriver driver = get();
        if (driver instanceof TakesScreenshot) {
            File file = new File(path);
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                IOUtils.write(bytes, new FileOutputStream(file));
                return true;
            } catch ( Exception e) {
                throw new RuntimeException("Failed to save screenshot to " + file, e);
            }
        }
        return false;
    }

}