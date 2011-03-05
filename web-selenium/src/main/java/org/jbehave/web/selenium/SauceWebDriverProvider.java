package org.jbehave.web.selenium;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceWebDriverProvider extends RemoteWebDriverProvider {

    public SauceWebDriverProvider() {
        super();
        desiredCapabilities.setCapability("name", "JBehave");
    }

    @Override
    public URL createRemoteURL() throws MalformedURLException {
        String username = System.getProperty("SAUCE_USERNAME");
        String access_key = System.getProperty("SAUCE_ACCESS_KEY");
        if (username == null) {
            throw new UnsupportedOperationException("SAUCE_USERNAME property name variable not specified");
        }
        if (access_key == null) {
            throw new UnsupportedOperationException("SAUCE_ACCESS_KEY property name variable not specified");
        }

        return new URL("http://" + username + ":" + access_key + "@ondemand.saucelabs.com/wd/hub");
    }

}