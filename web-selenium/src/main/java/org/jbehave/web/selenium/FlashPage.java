package org.jbehave.web.selenium;

import org.openqa.selenium.WebDriver;

public abstract class FlashPage extends WebDriverPage {

    public FlashPage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }    

    protected FlashDriver flashDriver() {
        makeNonLazy();
        WebDriver driver = webDriver();
        if ( driver instanceof FlashDriver ){
            return (FlashDriver)driver;
        }
        throw new FlashNotSupported(driver);
    }

    @SuppressWarnings("serial")
    public static class FlashNotSupported extends RuntimeException {

        public FlashNotSupported(WebDriver driver) {
            super("Flash not supported by WebDriver "+driver);
        }
        
    }

}
