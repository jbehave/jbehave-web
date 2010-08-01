package org.jbehave.web.examples.trader.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class Home extends TraderPage {

    public Home(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void open() {
        selenium.open("/trader-runner/");
    }

    public FindSteps findSteps(PageFactory factory){
        selenium.open("/trader-runner/steps/find.action");
        //clickId("find");
        return factory.findSteps();
    }
    
}
