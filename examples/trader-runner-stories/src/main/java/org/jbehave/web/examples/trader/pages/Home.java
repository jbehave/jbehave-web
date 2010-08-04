package org.jbehave.web.examples.trader.pages;

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
        click("link=Find Steps");
        waitForPageToLoad();
        return factory.findSteps();
    }
    
}
