package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.webdriver.WebDriverFactory;

public class PageFactory {

    private final WebDriverFactory driverFactory;

    public PageFactory(WebDriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public Home home(){
        return new Home(driverFactory);
    }

    public FindSteps findSteps() {
        return new FindSteps(driverFactory);
    }

    public RunStory runStory() {
        return new RunStory(driverFactory);
    }
    
}
