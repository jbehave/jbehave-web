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

    protected ThreadLocal<WebDriver> delegate = new ThreadLocal<WebDriver>();

    public WebDriver get() {
        WebDriver webDriver = delegate.get();
        if (webDriver == null) {
            throw new NullPointerException("WebDriver not setup yet for this thread. Are you using the right one of PerScenarioWebDriverSteps, PerStoryWebDriverSteps and PerStoriesWebDriverSteps");
        }
        return webDriver;
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