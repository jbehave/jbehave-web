package org.jbehave.web.selenium

import com.github.tanob.groobe.GrooBe
import com.google.common.base.Predicate
import java.util.concurrent.TimeUnit
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import static java.util.Arrays.asList

public class GroovyWebDriverPage extends WebDriverPage {

    public GroovyWebDriverPage(WebDriverProvider driverProvider) {
        super(driverProvider);
        GrooBe.activate()
    }

    def waitFor(int timeout, int retry, Class<? extends RuntimeException>... ignoreThese = new Class<? extends RuntimeException>[0], Closure block) {
        def wdw = new WebDriverWait(getDriverProvider().get(), timeout)
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