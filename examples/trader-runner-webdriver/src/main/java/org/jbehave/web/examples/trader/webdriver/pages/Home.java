package org.jbehave.web.examples.trader.webdriver.pages;

import java.util.concurrent.TimeUnit;

import org.jbehave.web.selenium.WebDriverProvider;

public class Home extends AbstractPage {

    public Home(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open() {
        get("http://localhost:8080/trader-runner/");
        manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

}
