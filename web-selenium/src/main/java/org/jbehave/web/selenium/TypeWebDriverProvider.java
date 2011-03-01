package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * Provides WebDriver instances of given type, instantiating it using the
 * default constructor.
 */
public class TypeWebDriverProvider extends DelegatingWebDriverProvider {

    private Class<? extends WebDriver> type;

    /**
     * Provides instances of {@link FirefoxDriver}s.
     */
    public TypeWebDriverProvider() {
        this(FirefoxDriver.class);
    }

    /**
     * Provides instances of a supplied type
     * 
     * @param type the WebDriver type to instantiate.
     */
    public TypeWebDriverProvider(Class<? extends WebDriver> type) {
        this.type = type;
    }

    public void initialize() {

        String profileName = System.getProperty("JBEHAVE_WEBDRIVER_FIREFOX_PROFILE");

        if (profileName != null && type.getName().equals(FirefoxDriver.class.getName())) {

            ProfilesIni allProfilesIni = new ProfilesIni();
            FirefoxProfile profile = allProfilesIni.getProfile(profileName);
            profile.setAcceptUntrustedCertificates(false);
            delegate.set(new FirefoxDriver(profile));

        } else {

            try {
                delegate.set(type.newInstance());
            } catch (InstantiationException e) {
                throw new UnsupportedOperationException(e);
            } catch (IllegalAccessException e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

}