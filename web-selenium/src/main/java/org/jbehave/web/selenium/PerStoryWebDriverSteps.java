package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;

/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to initialise and quit WebDriver before and after stories.
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
        driverProvider.quit();
    }

}