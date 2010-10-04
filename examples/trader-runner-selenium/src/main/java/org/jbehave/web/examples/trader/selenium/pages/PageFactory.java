package org.jbehave.web.examples.trader.selenium.pages;


import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class PageFactory {

    private final Selenium selenium;
    private final ConditionRunner conditionRunner;
    private Home home;
    private FindSteps findSteps;
    private RunStory runStory;

    public PageFactory(Selenium selenium, ConditionRunner conditionRunner) {
        this.selenium = selenium;
        this.conditionRunner = conditionRunner;
    }

    public Home home() {
        if (home == null) {
            home = new Home(selenium, conditionRunner);
        }
        return home;
    }

    public FindSteps findSteps() {
        if (findSteps == null) {
            findSteps = new FindSteps(selenium, conditionRunner);
        }
        return findSteps;
    }

    public RunStory runStory() {
        if (runStory == null) {
            runStory = new RunStory(selenium, conditionRunner);
        }
        return runStory;
    }

}
