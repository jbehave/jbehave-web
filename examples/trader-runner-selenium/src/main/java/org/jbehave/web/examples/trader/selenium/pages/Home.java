package org.jbehave.web.examples.trader.webdriver.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class Home extends TraderPage {

    public Home(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void open() {
        open("/trader-runner/");
    }

    public FindSteps findSteps(PageFactory factory){
        clickLink("Find Steps");
        waitForPageToLoad();
        return factory.findSteps();
    }
    
    public RunStory runStory(PageFactory factory){
        clickLink("Run Story");
        waitForPageToLoad();
        return factory.runStory();
    }
}
