package org.jbehave.web.examples.trader.selenium.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class RunStory extends AbstractPage {

    public RunStory(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }
    
    public void open() {
        clickLink("Run Story");
        waitForPageToLoad();
    }

    public void pageIsShown() {
        found("Story Input");
    }

    public void run(String story) {
        type("input", story);
        clickButton("Run");
        waitForPageToLoad();
    }

    public void runIsSuccessful() {
        found("Scenario");
        notFound("FAILED");       
        notFound("PENDING");       
    }

}
