package org.jbehave.web.selenium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteWebDriverProvider extends DelegatingWebDriverProvider {

    protected DesiredCapabilities desiredCapabilities;

    public RemoteWebDriverProvider(DesiredCapabilities desiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
    }

    public RemoteWebDriverProvider() {
        desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setVersion("3.6.");
        desiredCapabilities.setPlatform(Platform.WINDOWS);
        desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
    }

    public void initialize() {
        try {
            WebDriver remoteWebDriver = new ScreenShottingRemoteWebDriver(createRemoteURL(), desiredCapabilities);
            // Augmenter does not work. Resulting WebDriver is good for exclusive screen-shotting, but not normal
            // operation as 'session is null'
            //remoteWebDriver = new Augmenter().augment(remoteWebDriver);  // allows instanceof TakesScreenshot
            delegate.set(remoteWebDriver);
        } catch (MalformedURLException e) {
            banner();
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        } catch (UnsupportedOperationException e) {
            banner();
            e.printStackTrace();
            throw e;
        } catch (Throwable e) {
            banner();
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    private void banner() {
        System.out.println("************** Error Initializing WebDriverProvider *************");
    }

    public URL createRemoteURL() throws MalformedURLException {
        String url = System.getProperty("REMOTE_WEBDRIVER_URL");
        if (url == null) {
            throw new UnsupportedOperationException("REMOTE_WEBDRIVER_URL property name variable not specified");
        }
        return new URL(url);
    }

    static class ScreenShottingRemoteWebDriver extends RemoteWebDriver implements TakesScreenshot {

        public ScreenShottingRemoteWebDriver(URL remoteURL, DesiredCapabilities desiredCapabilities) {
            super(remoteURL, desiredCapabilities);
        }

        public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
            // Paul: Copied from FirefoxDriver.......
            // Get the screenshot as base64.
            String base64 = execute(DriverCommand.SCREENSHOT).getValue().toString();
            // ... and convert it.
            return target.convertFromBase64Png(base64);
        }
    }
}
