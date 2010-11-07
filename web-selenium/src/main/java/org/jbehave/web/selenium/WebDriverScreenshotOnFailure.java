package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;

/**
 * WebDriverSteps that save screenshot upon failure in a scenario outcome.
 */
public class WebDriverScreenshotOnFailure extends WebDriverSteps {

    public WebDriverScreenshotOnFailure(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure() throws Exception {
        String screenshotPath = "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
        driverProvider.saveScreenshotTo(screenshotPath);        
    }

}
