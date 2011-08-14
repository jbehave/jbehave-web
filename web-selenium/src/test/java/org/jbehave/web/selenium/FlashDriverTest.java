package org.jbehave.web.selenium;

import org.junit.Test;
import org.mockito.Mockito;
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

        when(delegate.executeScript("return arguments[0].GetVariable('aVar');", (Object[])null)).thenReturn("aValue");
        assertThat(driver.getVariable("aVar").toString(), equalTo("aValue"));
        verify(delegate).executeScript("return arguments[0].GetVariable('aVar');", (Object[])null);
        
        when(delegate.executeScript("return arguments[0].PercentLoaded();", (Object[])null)).thenReturn("100");
        assertThat(driver.percentLoaded(), equalTo(100));
        verify(delegate).executeScript("return arguments[0].PercentLoaded();", (Object[])null);
    }

}
