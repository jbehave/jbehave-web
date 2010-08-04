package org.jbehave.web.examples.trader.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class FindSteps extends TraderPage {

    public FindSteps(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void find(String step) {
        type("stepdocContext.matchingStep", step);
        click("find");
        waitForPageToLoad();
    }

    public void found(String stepdocs) {
        textIsVisible(stepdocs);       
    }

}
