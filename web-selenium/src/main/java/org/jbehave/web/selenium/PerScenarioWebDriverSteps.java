package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;


/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to start and stop WebDriver before and after scenarios.
 * 
 * @author Mauro Talevi
 */
public class PerScenarioWebDriverSteps extends WebDriverSteps {
    public PerScenarioWebDriverSteps(WebDriverFactory driverProvider) {
        super(driverProvider);
    }

    @BeforeScenario
    public void beforeScenario() throws Exception {
        driverFactory.initialize();
    }

    @AfterScenario
    public void afterScenario() throws Exception {
        getDriverFactory().get().quit();
    }

}
