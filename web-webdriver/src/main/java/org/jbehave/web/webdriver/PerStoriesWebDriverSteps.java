package org.jbehave.web.webdriver;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.openqa.selenium.WebDriver;

import static org.jbehave.web.webdriver.WebDriverConfiguration.defaultWebDriver;

/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to start and stop WebDriver before and after embeddable stories.
 * 
 * @author Mauro Talevi
 */
public class PerStoriesWebDriverSteps extends WebDriverSteps {

    public PerStoriesWebDriverSteps(WebDriver proxyDriver) {
        super(proxyDriver);
    }

    @BeforeStories
    public void beforeStories() throws Exception {
        setDriver(defaultWebDriver());
    }

    @AfterStories    
    public void afterStories() throws Exception {
        getDriver().close();
        setDriver(null);
    }

}
