package org.jbehave.web.examples.trader.selenium.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class RunStory extends TraderPage {

    public RunStory(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void run(String story) {
        type("input", story);
        clickButton("Run");
        waitForPageToLoad();
    }

    public void runIsSuccessful() {
        textIsVisible("Scenario");
        textIsNotVisible("FAILED");       
        textIsNotVisible("PENDING");       
    }

}
