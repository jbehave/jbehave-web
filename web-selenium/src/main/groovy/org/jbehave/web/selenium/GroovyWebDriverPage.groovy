package org.jbehave.web.selenium

import com.github.tanob.groobe.GrooBe
import org.jbehave.web.selenium.WebDriverPage
import org.jbehave.web.selenium.WebDriverProvider
import org.openqa.selenium.support.ui.WebDriverWait
import java.util.concurrent.TimeUnit
import org.openqa.selenium.WebDriver
import com.google.common.base.Predicate
import static java.util.Arrays.asList

public class GroovyWebDriverPage extends WebDriverPage {

    public GroovyWebDriverPage(WebDriverProvider driverProvider) {
        super(driverProvider);
        GrooBe.activate()
    }

    def waitFor(int timeout, int retry, Class<? extends RuntimeException>... ignoreThese = new Class<? extends RuntimeException>[0], Closure block) {
        def wdw = new WebDriverWait(webDriver(), timeout)
                        .pollingEvery(retry, TimeUnit.MILLISECONDS)
                        .ignoreAll(asList(ignoreThese))
                        .ignoring(NoSuchElementException.class) // standard
        return wdw.until(new Predicate<WebDriver>() {
            boolean apply(WebDriver wd) {
                block()
            }
        })
    }

}