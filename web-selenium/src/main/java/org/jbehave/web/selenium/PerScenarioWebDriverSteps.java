package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;

/**
 * Steps implementation that can be used before and after lifecycle in
 * WebDriver-based stories. It provides annotated methods to initialise
 * WebDriver before each scenario and quit WebDriver after each scenario.
 * 
 * @author Paul Hammant
 */
public class PerScenarioWebDriverSteps extends WebDriverSteps {
    public PerScenarioWebDriverSteps(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @BeforeScenario
    public void beforeScenario() throws Exception {
        driverProvider.initialize();
    }

    @AfterScenario
    public void afterScenario() throws Exception {
        driverProvider.get().quit();
    }

}
