package org.jbehave.web.selenium;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PropertyWebDriverProviderTest {

    private PropertyWebDriverProvider provider;

    @Test
    public void shouldAllowFirefoxAsDefultBasedOnBrowserProperty() {
        providerForBrowser(null);
        assertThat(provider.get(), instanceOf(FirefoxDriver.class));
    }

    @Test
    public void shouldAllowFirefoxBasedOnBrowserProperty() {
        providerForBrowser("firefox");
        assertThat(provider.get(), instanceOf(FirefoxDriver.class));
    }

    @Test
    public void shouldAllowChromeBasedOnBrowserProperty() {
        providerForBrowser("chrome");
        assertThat(provider.get(), instanceOf(ChromeDriver.class));
    }

    @Test
    public void shouldAllowHtmlUnitBasedOnBrowserProperty() {
        providerForBrowser("html");
        assertThat(provider.get(), instanceOf(HtmlUnitDriver.class));
    }


    @After
    public void tearDown() {
        if (provider != null) {
            provider.quit();
        }
    }


    private void providerForBrowser(String browser) {
        if ( browser != null ){
            System.setProperty("browser", browser);
        }
        provider = new PropertyWebDriverProvider();
        provider.initialize();
    }

}
