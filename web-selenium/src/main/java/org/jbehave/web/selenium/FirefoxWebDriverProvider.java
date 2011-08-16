package org.jbehave.web.selenium;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
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
    public static final Map<Integer,Long> driverToThreadMap = new HashMap<Integer,Long>(11);
    public void initialize() {
        String profileName = System.getProperty(FIREFOX_PROFILE);
        final FirefoxBinary binary = new FirefoxBinary();
        decorateFirefoxBinary(binary);
        if (profileName != null) {
            ProfilesIni allProfilesIni = new ProfilesIni();
            FirefoxProfile profile = allProfilesIni.getProfile(profileName);
            profile.setAcceptUntrustedCertificates(false);
            final FirefoxDriver firefoxDriver = new FirefoxDriver(binary, profile);
            delegate.set(firefoxDriver);
        } else {
            WebDriver fireFoxDriver = null;

            final WebDriver[] fireFoxDriverz = new WebDriver[1];
            fireFoxDriver = new FirefoxDriver(binary, null) {

                @Override
                protected void stopClient(){
                    super.stopClient();
                    driverToThreadMap.remove(System.identityHashCode(fireFoxDriverz[0]));
                    FirefoxWebDriverProvider.this.ending();
                }

                @Override
                public void close() {
                    super.close();
                }

            };

            fireFoxDriverz[0] = fireFoxDriver;

            delegate.set(fireFoxDriver);
            if(driverToThreadMap.containsKey(System.identityHashCode(fireFoxDriver))){
                // TODO - take out all this driverToThreadMap
                // For debugging during development
                // throw new Error("Duplicate Driver !!!!!!!");
            }else{
              driverToThreadMap.put(System.identityHashCode(fireFoxDriver),Thread.currentThread().getId());
            }

        }


    }

    protected void ending() {
    }
    protected void decorateFirefoxBinary(FirefoxBinary binary) {
    }

}