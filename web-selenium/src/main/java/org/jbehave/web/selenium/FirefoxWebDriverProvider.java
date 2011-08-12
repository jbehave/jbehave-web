package org.jbehave.web.selenium;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * Provides Firefox WebDriver instances, using a profile if the name 
 * is provided via the system property {@link #FIREFOX_PROFILE}.
 */
public class FirefoxWebDriverProvider extends DelegatingWebDriverProvider {

    public static final String FIREFOX_PROFILE = "JBEHAVE_WEBDRIVER_FIREFOX_PROFILE";

    public void initialize() {
        String profileName = System.getProperty(FIREFOX_PROFILE);

        FirefoxBinary firefoxBinary = new FirefoxBinary();
        decorateFirefoxBinary(firefoxBinary);

        if (profileName != null) {
            ProfilesIni allProfilesIni = new ProfilesIni();
            FirefoxProfile profile = allProfilesIni.getProfile(profileName);
            profile.setAcceptUntrustedCertificates(false);
            delegate.set(new FirefoxDriver(firefoxBinary, profile));
        } else {
            delegate.set(new FirefoxDriver(firefoxBinary, null));
        }
    }

    protected void decorateFirefoxBinary(FirefoxBinary firefoxBinary) {
    }

}