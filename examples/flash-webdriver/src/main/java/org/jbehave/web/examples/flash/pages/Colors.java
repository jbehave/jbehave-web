package org.jbehave.web.examples.flash.pages;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverProvider;

public class Colors extends FlashPage {

    public Colors(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open() {
        get("http://localhost:8080/flash/colors.html");
        manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public int percentLoaded() {
        return flashDriver().percentLoaded();
    }

    public String getColor() {
        return flashDriver().call("getColor").toString();
    }

    public String getSquareLabel() {
        return flashDriver().call("getSquareLabel").toString();
    }

    public void clickOnSquare() {
        flashDriver().click();        
    }

    public void changeLabel(String label) {
        flashDriver().call("setSquareLabel", label);        
    }

}
