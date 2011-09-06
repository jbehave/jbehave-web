package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import com.thoughtworks.selenium.Selenium;

/**
 * Steps implementation that can be used before and after lifecycle in
 * Selenium-based stories. It provides annotated methods to start Selenium
 * before all stories and close/stop Selenum after all stories.
 * 
 * <p>
 * <b>NOTE:</b> using this implementation requires the use of a same-thread
 * {@link ExecutorService} to be configured in the Embedder, e.g. Guava's
 * {@link MoreExecutors.sameThreadExecutor()},
 * </p>
 * 
 * @author Mauro Talevi
 */
public class PerStoriesSeleniumSteps extends SeleniumSteps {

    public PerStoriesSeleniumSteps() {
        super();
    }

    public PerStoriesSeleniumSteps(Selenium selenium) {
        super(selenium);
    }

    @BeforeStories
    public void beforeStories() throws Exception {
        selenium.start();
    }

    @AfterStories
    public void afterStories() throws Exception {
        selenium.close();
        selenium.stop();
    }

}
