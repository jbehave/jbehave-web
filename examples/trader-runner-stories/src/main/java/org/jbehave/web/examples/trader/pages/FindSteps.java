package org.jbehave.web.examples.trader.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.Presence;

public class FindSteps extends TraderPage {

    public FindSteps(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void find(String step) {
        waitFor(new Presence("id=find"));
        typeId("stepdocContext.matchingStep", step);
        clickId("find");               
    }

    public void found(String stepdocs) {
        textIsVisible(stepdocs);       
    }

}
