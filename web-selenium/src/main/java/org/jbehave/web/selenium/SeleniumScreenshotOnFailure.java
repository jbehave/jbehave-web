package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;

import java.io.File;

/**
 * SeleniumSteps that capture screenshot upon failure in a scenario outcome.
 */
public class SeleniumScreenshotOnFailure extends SeleniumSteps {

    public SeleniumScreenshotOnFailure(Selenium selenium) {
        super(selenium);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterFailingScenario() throws Exception {
        String screenshotPath = "target" + File.separator + "screenshots" + File.separator + "failed-scenario-" + System.currentTimeMillis() + ".png";
        selenium.captureScreenshot(screenshotPath);
    }
    
}
