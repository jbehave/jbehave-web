package org.jbehave.web.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.selenium.fluent.FluentWebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.openqa.selenium.By.tagName;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class FluentWebDriverPageTest {

    @Test
    public void testAllTheMethods() throws IllegalAccessException, InvocationTargetException {

        Method[] fwdpMethods = FluentWebDriverPage.class.getDeclaredMethods();
        Method[] fwdMethods = FluentWebDriver.class.getDeclaredMethods();

        WebDriver wd = mock(WebDriver.class);
        WebElement we = mock(WebElement.class);
        WebDriverProvider wdp = mock(WebDriverProvider.class);
        when(wdp.get()).thenReturn(wd);
        FluentWebDriverPage fwdp = new FluentWebDriverPage(wdp) {};
        By byID = By.id("foo");


        for (Method method : fwdMethods) {
            Method matchingMethod = null;
            for (Method method2 : fwdpMethods) {
                if (method.getReturnType().isAssignableFrom(method2.getReturnType()) &&
                    method.getName().equals(method2.getName()) &&
                    method.getParameterTypes().length ==  method2.getParameterTypes().length) {
                    matchingMethod = method2;
                }
            }
            if (notExcluded(method.getName())) {
                if (matchingMethod == null) {
                    fail("Method: " + method + " not in in FluentWebDriverPage");
                } else {
                    String rootElemName = method.getName().replace("link", "a");
                    if (method.getParameterTypes().length == 0) {
                        when(wd.findElement(tagName(rootElemName))).thenReturn(we);
                        when(we.getTagName()).thenReturn(rootElemName);
                        Object foo = matchingMethod.invoke(fwdp);
                        assertNotNull(foo);
                    } else if (method.getParameterTypes()[0] == By.class) {
                        when(wd.findElement(byID)).thenReturn(we);
                        when(we.getTagName()).thenReturn(rootElemName);
                        Object foo = matchingMethod.invoke(fwdp, byID);
                        assertNotNull(foo);
                    } else {
                        Object foo = matchingMethod.invoke(fwdp, secs(1));
                        assertNotNull(foo);
                    }
                }
            }
        }
    }

    private boolean notExcluded(String name) {
        return !name.equals("makeFluentWebElements")
        && !name.equals("newFluentWebElements")
        && !name.equals("actualFindIt")
        && !name.equals("actualFindThem")
        && !name.equals("findIt")
        && !name.equals("findThem");
    }

}
