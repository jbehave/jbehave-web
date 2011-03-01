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
            String msg = "***  WebDriver has not initialized.  This either because the initialize() method of " + this.getClass().getName() + " failed.\n"
                    + "Refer the build log for error message/exceptions previously sent.\n"
                    + "Refer also http://jira.codehaus.org/browse/JBEHAVE-400) for who the build is not halted sooner.\n"
                    + "OR WebDriver may not have been setup yet for this thread. Are you using the right impl class: PerScenarioWebDriverSteps versus PerStoryWebDriverSteps versus PerStoriesWebDriverSteps?";
            banner(); banner(); banner();
            System.out.println(msg);
            banner(); banner(); banner();
            throw new NullPointerException(msg);
        }
        return webDriver;
    }

    private void banner() {
        System.out.println("*************************************************************************");
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