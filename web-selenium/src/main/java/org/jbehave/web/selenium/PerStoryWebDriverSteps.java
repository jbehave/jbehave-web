package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;

/**
 * Steps implementation that can be used before and after lifecycle in
 * WebDriver-based stories. It provides annotated methods to initialise
 * WebDriver before each story and quit WebDriver after each story.
 * 
 * @author Paul Hammant
 */
public class PerStoryWebDriverSteps extends WebDriverSteps {

    public PerStoryWebDriverSteps(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @BeforeStory
    public void beforeStory() throws Exception {
        driverProvider.initialize();
    }

    @AfterStory
    public void afterStory() throws Exception {
        driverProvider.end();
    }

}