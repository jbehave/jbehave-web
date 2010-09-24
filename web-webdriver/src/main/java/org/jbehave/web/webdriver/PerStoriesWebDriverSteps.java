package org.jbehave.web.webdriver;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;


/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to start and stop WebDriver before and after embeddable stories.
 * 
 * @author Mauro Talevi
 */
public class PerStoriesWebDriverSteps extends WebDriverSteps {

    public PerStoriesWebDriverSteps(WebDriverProxy proxyDriver) {
        super(proxyDriver);
    }

    @BeforeStories
    public void beforeStories() throws Exception {
        driverProxy.newWebDriver();
    }

    @AfterStories    
    public void afterStories() throws Exception {
        getDriver().quit();
    }

}
