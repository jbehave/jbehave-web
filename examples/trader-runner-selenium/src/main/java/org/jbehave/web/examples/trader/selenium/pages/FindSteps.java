package org.jbehave.web.examples.trader.selenium.pages;

import java.util.List;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class FindSteps extends TraderPage {

    public FindSteps(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void find(String step) {
        type("matchingStep", step);
        clickButton("Find");
        waitForPageToLoad();
    }

    public void found(String step) {
        textIsVisible(step);       
    }

    public void found(List<String> steps) {
        for ( String step : steps ){
            textIsVisible(step);
        }
    }

    public void viewWithMethods() {
        selectByLabel("viewSelect", "WITH_METHODS");
        waitForPageToLoad();        
    }

    public void sortByPattern() {
        selectByLabel("sortingSelect", "BY_PATTERN");
        waitForPageToLoad();        
    }

}
