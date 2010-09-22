package org.jbehave.web.examples.trader.selenium.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class PageFactory {

    private final Selenium selenium;
    private final ConditionRunner conditionRunner;

    public PageFactory(Selenium selenium, ConditionRunner conditionRunner) {
        this.selenium = selenium;
        this.conditionRunner = conditionRunner;
    }

    public Home home(){
        return new Home(selenium, conditionRunner);
    }

    public FindSteps findSteps() {
        return new FindSteps(selenium, conditionRunner);
    }

    public RunStory runStory() {
        return new RunStory(selenium, conditionRunner);
    }
    
}
