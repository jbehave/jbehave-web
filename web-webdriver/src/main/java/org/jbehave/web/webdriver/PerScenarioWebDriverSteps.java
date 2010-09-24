package org.jbehave.web.webdriver;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;


/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to start and stop WebDriver before and after scenarios.
 * 
 * @author Mauro Talevi
 */
public class PerScenarioWebDriverSteps extends WebDriverSteps {
    public PerScenarioWebDriverSteps(WebDriverProxy driverProxy) {
        super(driverProxy);
    }

    @BeforeScenario
    public void beforeScenario() throws Exception {
        driverProxy.newWebDriver();
    }

    @AfterScenario
    public void afterScenario() throws Exception {
        getDriver().quit();
    }

}
