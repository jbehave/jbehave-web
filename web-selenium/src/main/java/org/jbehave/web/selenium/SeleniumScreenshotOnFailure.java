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
        selenium.captureScreenshot(makeScreenShotFilePath());
    }

    /**
     * Override this if you want to participate in the file-path of the screen shot
     * @return the path
     */
    private String makeScreenShotFilePath() {
        return "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
    }

}
