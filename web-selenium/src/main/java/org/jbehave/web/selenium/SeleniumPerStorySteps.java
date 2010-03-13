package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import org.jbehave.scenario.annotations.AfterStory;
import org.jbehave.scenario.annotations.BeforeStory;
import org.jbehave.scenario.steps.StepsConfiguration;

public abstract class SeleniumPerStorySteps extends SeleniumSteps {

    public SeleniumPerStorySteps() {
    }

    public SeleniumPerStorySteps(StepsConfiguration configuration) {
        super(configuration);
    }

    public SeleniumPerStorySteps(Selenium selenium) {
        super(selenium);
    }

    public SeleniumPerStorySteps(SeleniumStepsConfiguration configuration) {
        super(configuration);
    }

    @BeforeStory
    public void beforeStory() throws Exception {
        selenium.start();
    }

    @AfterStory
    public void afterStory() throws Exception {
        selenium.close();
        selenium.stop();
    }


}