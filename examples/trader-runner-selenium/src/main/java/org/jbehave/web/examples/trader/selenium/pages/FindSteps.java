package org.jbehave.web.examples.trader.selenium.pages;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class FindSteps extends AbstractPage {

    public FindSteps(Selenium selenium, ConditionRunner conditionRunner) {
        super(selenium, conditionRunner);
    }

    public void open() {
        clickLink("Find Steps");
        waitForPageToLoad();
    }

    public void pageIsShown() {
        found("Patterns and methods matching the textual step");
    }

    public void find(String step) {
        type("matchingStep", step);
        clickButton("Find");
        waitForPageToLoad();
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
