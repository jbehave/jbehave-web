package org.jbehave.web.selenium;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PropertyWebDriverProviderTest {

    @Test
    public void shouldAllowDifferentProvidersBasedOnBrowserProperty(){
        assertThat(providerForBrowser(null).get(), instanceOf(FirefoxDriver.class));
        assertThat(providerForBrowser("firefox").get(), instanceOf(FirefoxDriver.class));
        assertThat(providerForBrowser("chrome").get(), instanceOf(ChromeDriver.class));
        assertThat(providerForBrowser("html").get(), instanceOf(HtmlUnitDriver.class));
    }

    private PropertyWebDriverProvider providerForBrowser(String browser) {
        if ( browser != null ){
            System.setProperty("browser", browser);
        }
        PropertyWebDriverProvider provider = new PropertyWebDriverProvider();
        provider.initialize();
        return provider;
    }

}
