package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;

import com.thoughtworks.selenium.Selenium;

/**
 * SeleniumSteps that capture screenshot upon failure in a scenario outcome.
 */
public class SeleniumScreenshotOnFailure extends SeleniumSteps {

    public SeleniumScreenshotOnFailure(Selenium selenium) {
        super(selenium);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterFailingScenario() throws Exception {
        String screenshotPath = "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
        selenium.captureScreenshot(screenshotPath);
    }
    
}
