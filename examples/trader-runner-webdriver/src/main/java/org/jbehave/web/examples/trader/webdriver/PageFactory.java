package org.jbehave.web.examples.trader.webdriver;

import org.jbehave.web.examples.trader.webdriver.pages.FindSteps;
import org.jbehave.web.examples.trader.webdriver.pages.Home;
import org.jbehave.web.examples.trader.webdriver.pages.RunStory;
import org.jbehave.web.selenium.WebDriverProvider;

public class PageFactory {

    private final WebDriverProvider driverProvider;

    public PageFactory(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Home home(){
        return new Home(driverProvider);
    }

    public FindSteps findSteps() {
        return new FindSteps(driverProvider);
    }

    public RunStory runStory() {
        return new RunStory(driverProvider);
    }
    
}
