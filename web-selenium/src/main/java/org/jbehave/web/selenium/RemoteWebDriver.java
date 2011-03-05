package org.jbehave.web.selenium;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
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
    }

    public void initialize() {
        try {
            delegate.set(new RemoteWebDriver(createRemoteURL(), desiredCapabilities));
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
}
