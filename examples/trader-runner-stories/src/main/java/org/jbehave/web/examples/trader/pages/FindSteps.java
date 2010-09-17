package org.jbehave.web.examples.trader.pages;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

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
        String text = StringUtils.join(steps, "\n");
        MatcherAssert.assertThat(text, Matchers.equalTo(text("stepdocs")));
    }

    public void sortByPattern() {
        selectByLabel("sortingSelect", "BY_PATTERN");
        waitForPageToLoad();        
    }

}
