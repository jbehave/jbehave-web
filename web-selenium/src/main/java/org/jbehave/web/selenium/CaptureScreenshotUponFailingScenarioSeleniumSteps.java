package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;

import com.thoughtworks.selenium.Selenium;

public class CaptureScreenshotUponFailingScenarioSeleniumSteps extends PerScenarioSeleniumSteps {

    public CaptureScreenshotUponFailingScenarioSeleniumSteps(Selenium selenium) {
        super(selenium);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterFailingScenario() throws Exception {
        String screenshotPath = "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
        selenium.captureScreenshot(screenshotPath);
    }

}
