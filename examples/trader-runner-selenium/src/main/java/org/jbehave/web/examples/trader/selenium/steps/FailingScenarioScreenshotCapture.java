package org.jbehave.web.examples.trader.selenium.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.selenium.PerScenarioSeleniumSteps;

import com.thoughtworks.selenium.Selenium;

public class FailingScenarioScreenshotCapture extends PerScenarioSeleniumSteps {

    public FailingScenarioScreenshotCapture(Selenium selenium) {
        super(selenium);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterFailingScenario() throws Exception {
        String home = System.getenv("HOME");
        selenium.captureScreenshot(home+"/failedScenario.png");
    }

}
