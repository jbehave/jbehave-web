package org.jbehave.web.webdriver;

import org.openqa.selenium.WebDriver;

public abstract class WebDriverPage {

    protected final WebDriver driver;

    public WebDriverPage(WebDriver driver) {
        this.driver = driver;
    }

}
