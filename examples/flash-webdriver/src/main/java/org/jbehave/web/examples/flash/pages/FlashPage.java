package org.jbehave.web.examples.flash.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.List;

import org.jbehave.web.selenium.FlashDriver;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriver;

public abstract class FlashPage extends WebDriverPage {

    public FlashPage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }
    
    public void found(String text) {
        found(getPageSource(), text);
    }

    public void found(String pageSource, String text) {
        if (!pageSource.contains(escapeHtml(text))) {
            fail("Text: '" + text + "' not found in page '" + pageSource + "'");
        }
    }

    public void found(List<String> texts) {
        for (String text : texts) {
            found(text);
        }
    }

    public void notFound(String text) {
        notFound(getPageSource(), text);
    }

    public void notFound(String pageSource, String text) {
        assertThat(pageSource.contains(escapeHtml(text)), is(false));
    }

    private String escapeHtml(String text) {
        return text.replace("<", "&lt;").replace(">", "&gt;");
    }

    protected FlashDriver flashDriver() {
        WebDriver driver = this.webDriver();
        if ( driver instanceof FlashDriver ){
            return (FlashDriver)driver;
        }
        throw new RuntimeException("WebDriver not Flash enabled" + driver);
    }

}
