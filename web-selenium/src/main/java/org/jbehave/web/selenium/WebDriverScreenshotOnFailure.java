package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;

/**
 * WebDriverSteps that save screen-shot upon failure in a scenario outcome.
 * Not all WebDriver implement screen-shot capability
 */
public class WebDriverScreenshotOnFailure extends WebDriverSteps {

    public WebDriverScreenshotOnFailure(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure() throws Exception {
        String screenshotPath = "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
        driverProvider.saveScreenshotTo(screenshotPath);
        System.out.println("Screenshot may or may not have been saved to '" + screenshotPath +"' depending on whether the WebDriver implementation supports screen shots");
    }

}
