package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;

import com.thoughtworks.selenium.Selenium;

/**
 * Steps implementation that can be used in Selenium-based stories. It provides
 * annotated methods to start and stop Selenium before and after embeddable stories.
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
