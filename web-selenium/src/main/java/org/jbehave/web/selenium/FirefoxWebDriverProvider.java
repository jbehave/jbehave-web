package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * Provides WebDriver instances of given type, instantiating it using the
 * default constructor.
 */
public class FirefoxWebDriverProvider extends DelegatingWebDriverProvider {

    public void initialize() {

        String profileName = System.getProperty("JBEHAVE_WEBDRIVER_FIREFOX_PROFILE");

        if (profileName != null) {

            ProfilesIni allProfilesIni = new ProfilesIni();
            FirefoxProfile profile = allProfilesIni.getProfile(profileName);
            profile.setAcceptUntrustedCertificates(false);
            delegate.set(new FirefoxDriver(profile));

        } else {
            delegate.set(new FirefoxDriver());
        }
    }

}