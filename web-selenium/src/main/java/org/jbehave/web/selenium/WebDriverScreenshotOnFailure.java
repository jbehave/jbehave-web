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
        driverProvider.saveScreenshotTo(makeScreenShotFilePath());
    }

    /**
     * Override this if you want to participate in the file-path of the screen shot
     * @return the path
     */
    protected String makeScreenShotFilePath() {
        return "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
    }

}
