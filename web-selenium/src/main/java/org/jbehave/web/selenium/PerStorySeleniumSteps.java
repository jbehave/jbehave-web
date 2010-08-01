package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;

import com.thoughtworks.selenium.Selenium;

/**
 * Steps implementation that can be used in Selenium-based stories. It
 * provides annotated methods to start and stop Selenium before and after
 * stories. 
 *
 * @author Mauro Talevi
 */
public class PerStorySeleniumSteps extends SeleniumSteps {

    public PerStorySeleniumSteps() {
        super();
    }

    public PerStorySeleniumSteps(Selenium selenium) {
        super(selenium);
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