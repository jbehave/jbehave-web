package org.jbehave.web.examples.flash.pages;

import org.jbehave.web.selenium.FlashDriver;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriver;

public abstract class FlashPage extends WebDriverPage {

    public FlashPage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }    

    protected FlashDriver flashDriver() {
        WebDriver driver = this.webDriver();
        if ( driver instanceof FlashDriver ){
            return (FlashDriver)driver;
        }
        throw new RuntimeException("WebDriver not Flash enabled" + driver);
    }

}
