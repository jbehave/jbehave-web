package org.jbehave.web.examples.trader.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class PageFactory {

    private final Selenium selenium;
    private final ConditionRunner conditionRunner;

    public PageFactory(Selenium selenium, ConditionRunner conditionRunner) {
        this.selenium = selenium;
        this.conditionRunner = conditionRunner;
    }

    public Home home(){
        return new Home(selenium, conditionRunner);
    }
    
}
