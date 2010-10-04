package org.jbehave.web.examples.trader.selenium.pages;

import java.util.List;

import org.jbehave.web.selenium.SeleniumPage;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public abstract class AbstractPage extends SeleniumPage {

    public AbstractPage(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }
 
    public void found(String text) {
        textIsVisible(text);
    }

    public void found(List<String> texts) {
        for (String text : texts) {
            found(text);
        }
    }

    public void notFound(String text) {
        textIsNotVisible(text);
    }

}
