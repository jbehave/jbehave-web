package org.jbehave.web.examples.trader.selenium.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class Home extends AbstractPage {

    public Home(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void open() {
        open("/trader-runner/");
    }

}
