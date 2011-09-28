package org.jbehave.web.selenium;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LazyWebDriverTest {

    @Test
    public void testLazyWebDriverMethods() {


        final WebDriver wd = mock(WebDriver.class);

        WebDriverProvider wdp = new WebDriverProvider() {
            public WebDriver get() {
                return wd;
            }

            public void initialize() {
            }

            public void end() {
            }

            public boolean saveScreenshotTo(String path) {
                return false;
            }
        };

        LazyWebDriver lwd = new LazyWebDriver(wdp);

        lwd.get("foo");
        verify(wd).get("foo");

        lwd.close();
        verify(wd).close();

        lwd.quit();
        verify(wd).quit();

        WebDriver.TargetLocator targetLocator = mock(WebDriver.TargetLocator.class);
        when(wd.switchTo()).thenReturn(targetLocator);
        assertEquals(targetLocator, lwd.switchTo());

        WebElement webElement = mock(WebElement.class);
        when(wd.findElement(By.id("foo"))).thenReturn(webElement);
        assertEquals(webElement, lwd.findElement(By.id("foo")));

        @SuppressWarnings("unchecked")
        List<WebElement> webElements = mock(List.class);
        when(wd.findElements(By.id("foo"))).thenReturn(webElements);
        assertEquals(webElements, lwd.findElements(By.id("foo")));

        String url = "foo";
        when(wd.getPageSource()).thenReturn(url);
        assertEquals(url, lwd.getPageSource());

        String ps = "foo";
        when(wd.getCurrentUrl()).thenReturn(ps);
        assertEquals(ps, lwd.getCurrentUrl());

        String wh = "foo";
        when(wd.getWindowHandle()).thenReturn(wh);
        assertEquals(wh, lwd.getWindowHandle());

        WebDriver.Options options = mock(WebDriver.Options.class);
        when(wd.manage()).thenReturn(options);
        assertEquals(options, lwd.manage());

        WebDriver.Navigation navigation = mock(WebDriver.Navigation.class);
        when(wd.navigate()).thenReturn(navigation);
        assertEquals(navigation, lwd.navigate());


    }


}
