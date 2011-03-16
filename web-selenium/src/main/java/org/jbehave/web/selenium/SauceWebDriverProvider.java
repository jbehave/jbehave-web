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

        return new URL("http://" + getSauceCredentials() + "@ondemand.saucelabs.com/wd/hub");
    }

    public static String getSauceUser() {
        String username = System.getProperty("SAUCE_USERNAME");
        if (username == null) {
            throw new UnsupportedOperationException("SAUCE_USERNAME property name variable not specified");
        }
        return username;
    }

    public static String getSauceCredentials() {
        String access_key = System.getProperty("SAUCE_ACCESS_KEY");
        if (access_key == null) {
            throw new UnsupportedOperationException("SAUCE_ACCESS_KEY property name variable not specified");
        }
        return getSauceUser() + ":" + access_key;
    }

}