package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

/**
 * Steps implementation that can be used before and after lifecycle in
 * WebDriver-based stories. It provides annotated methods to initialise
 * WebDriver before all stories and quit WebDriver after all stories.
 * 
 * <p>
 * <b>NOTE:</b> using this implementation requires the use of a same-thread
 * {@link ExecutorService} to be configured in the Embedder, e.g. Guava's
 * {@link MoreExecutors.sameThreadExecutor()},
 * </p>
 * 
 * @author Paul Hammant
 */
public class PerStoriesWebDriverSteps extends WebDriverSteps {

    public PerStoriesWebDriverSteps(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @BeforeStories
    public void beforeStories() throws Exception {
        driverProvider.initialize();
    }

    @AfterStories
    public void afterStories() throws Exception {
        driverProvider.get().quit();
    }

}
