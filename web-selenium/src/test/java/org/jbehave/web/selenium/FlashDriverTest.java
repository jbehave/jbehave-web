package org.jbehave.web.selenium;

import org.hamcrest.Matchers;
import org.jbehave.web.selenium.FlashDriver.JavascriptNotSupported;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlashDriverTest {

    @Test
    public void shouldExecuteJavascriptMethodsOnFlashObject() {
        FirefoxDriver delegate = Mockito.mock(FirefoxDriver.class);
        FlashDriver driver = new FlashDriver(delegate, "flashObjectId");
        
        driver.click();
        verify(delegate).executeScript("return arguments[0].click();", (Object[])null);

        when(delegate.executeScript("return arguments[0].PercentLoaded();", (Object[])null)).thenReturn("100");
        assertThat(driver.percentLoaded(), equalTo(100));
        verify(delegate).executeScript("return arguments[0].PercentLoaded();", (Object[])null);
    }

    @Test(expected=JavascriptNotSupported.class)
    public void shouldThrowAnExceptionIfWebDriverIsNotAJavascriptExecutor() {
        WebDriver delegate = Mockito.mock(WebDriver.class);
        FlashDriver driver = new FlashDriver(delegate, "flashObjectId");        
        driver.click();
    }

    @Test
    public void shouldProvideFlashDriver() {
        FlashWebDriverProvider provider = new FlashWebDriverProvider("flashObjectId");
        provider.initialize();
        assertThat(provider.get(), Matchers.instanceOf(FlashDriver.class));
    }
    
}
