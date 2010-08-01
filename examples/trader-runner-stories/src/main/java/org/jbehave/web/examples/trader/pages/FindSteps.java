package org.jbehave.web.examples.trader.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.Presence;

public class FindSteps extends TraderPage {

    public FindSteps(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void find(String step) {
        System.out.println("Looking for "+step);
        waitFor(new Presence("id=find"));
        clickId("find");       
    }

}
