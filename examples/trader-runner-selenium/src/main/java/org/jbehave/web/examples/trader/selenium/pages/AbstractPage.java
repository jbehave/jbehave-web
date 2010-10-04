package org.jbehave.web.examples.trader.selenium.pages;

import org.jbehave.web.selenium.SeleniumPage;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public abstract class AbstractPage extends SeleniumPage {

    public AbstractPage(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }
    
}
