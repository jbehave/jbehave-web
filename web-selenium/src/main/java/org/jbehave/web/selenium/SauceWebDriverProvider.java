package org.jbehave.web.selenium;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *  Allows to connect to <a href="http://saucelabs.com/">Sauce Labs</a> to run 
 *  Selenium tests in the cloud.  Requires Sauce credentials, username and access key, which
 *  can be provided via system properties "SAUCE_USERNAME" and "SAUCE_ACCESS_KEY".
 *
 *  Firefox on Windows is the default browser choice. This is done via DesiredCapabilities
 *  passed in through the constructor.  Like so -
 *
 *      DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
 *      desiredCapabilities.setVersion("3.6.");
 *      desiredCapabilities.setPlatform(Platform.WINDOWS);
 *      desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
 *
 */
public class SauceWebDriverProvider extends RemoteWebDriverProvider {

    /**
     *
     * @param desiredCapabilities
     */
    public SauceWebDriverProvider(DesiredCapabilities desiredCapabilities) {
        super(desiredCapabilities);
    }

    /**
     * With default capabilities and a selenium version specified in getSeleniumVersion()
     * @see RemoteWebDriverProvider#defaultDesiredCapabilities()
     * @see SauceWebDriverProvider#getSeleniumVersion()
     */
    public SauceWebDriverProvider() {
        super();
        desiredCapabilities.setCapability("name", "JBehave");
        desiredCapabilities.setCapability("selenium-version", getSeleniumVersion());
    }

    /**
     * Get selenium version from System property 'selenium.version' if there.
     * User '2.13.0' if property not set.
     * @return Selenium version.
     */
    protected String getSeleniumVersion() {
        String seVersion = System.getProperty("selenium.version");
        if (seVersion == null) {
            seVersion = "2.13.0";
        }
        return seVersion;
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

    public static String getSauceAccessKey() {
        String access_key = System.getProperty("SAUCE_ACCESS_KEY");
        if (access_key == null) {
            throw new UnsupportedOperationException("SAUCE_ACCESS_KEY property name variable not specified");
        }
        return access_key;
    }

    public static String getSauceCredentials() {
        return getSauceUser() + ":" + getSauceAccessKey();
    }

}