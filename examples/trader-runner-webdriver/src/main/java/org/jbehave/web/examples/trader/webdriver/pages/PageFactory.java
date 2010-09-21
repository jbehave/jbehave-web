package org.jbehave.web.examples.trader.webdriver.pages;

import org.openqa.selenium.WebDriver;

public class PageFactory {

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
    }

    public Home home(){
        return new Home(driver);
    }

    public FindSteps findSteps() {
        return new FindSteps(driver);
    }

    public RunStory runStory() {
        return new RunStory(driver);
    }
    
}
