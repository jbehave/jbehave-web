package org.jbehave.web.selenium;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class PropertyWebDriverProviderTest {

    private WebDriverProvider provider;

    @After
    public void after() {
        if (provider != null) {
            provider.get().close();
        }
    }

    @Test
    public void shouldSupportFirefoxByDefault() {
        createProviderForProperty(null);
        assertThat(provider.get(), instanceOf(FirefoxDriver.class));
    }

    @Test
    public void shouldSupportFirefoxByProperty() {
        createProviderForProperty("firefox");
        assertThat(provider.get(), instanceOf(FirefoxDriver.class));
    }

    @Test
    @Ignore("Only when Chrome is available")
    public void shouldSupportChromeByProperty() {
        createProviderForProperty("chrome");
        assertThat(provider.get(), instanceOf(ChromeDriver.class));
    }

    @Test
    @Ignore("Only when IE is available")
    public void shouldSupportIEByProperty() {
        createProviderForProperty("ie");
        assertThat(provider.get(), instanceOf(InternetExplorerDriver.class));
    }

    @Test
    @Ignore("Only when Android is available")
    public void shouldSupportAndroidByProperty() {
        createProviderForProperty("android");
        assertThat(provider.get(), instanceOf(AndroidDriver.class));
    }

    private void createProviderForProperty(String browser) {
        if (browser != null) {
            System.setProperty("browser", browser);
        }
        provider = new PropertyWebDriverProvider();
        provider.initialize();
    }

}
