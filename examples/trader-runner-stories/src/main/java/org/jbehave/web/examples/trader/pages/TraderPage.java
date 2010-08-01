package org.jbehave.web.examples.trader.pages;

import org.jbehave.web.selenium.SeleniumPage;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class TraderPage extends SeleniumPage {

    public TraderPage(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void clickButton(String name) {
        selenium.click("//input[@value='"+name+"']");
    }

    public void clickLink(String link) {
        selenium.click("link="+link);
    }

}