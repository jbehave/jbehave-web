package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to initialise and quit WebDriver before and after
 * embeddable stories.
 * 
 * @author Paul Hammant
 */
public class PerStoriesWebDriverSteps extends WebDriverSteps {

    public PerStoriesWebDriverSteps(WebDriverFactory driverFactory) {
        super(driverFactory);
    }

    @BeforeStories
    public void beforeStories() throws Exception {
        driverFactory.initialize();
    }

    @AfterStories
    public void afterStories() throws Exception {
        driverFactory.get().quit();
    }

}
