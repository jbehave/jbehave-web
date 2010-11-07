package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverSteps;

public class SaveScreenshotUponFailingScenarioWebDriverSteps extends WebDriverSteps {

    public SaveScreenshotUponFailingScenarioWebDriverSteps(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure() throws Exception {
        String screenshotPath = "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
        driverProvider.saveScreenshotTo(screenshotPath);        
    }

    @AfterScenario(uponOutcome = Outcome.SUCCESS)
    public void afterScenario() throws Exception {
    }


}
