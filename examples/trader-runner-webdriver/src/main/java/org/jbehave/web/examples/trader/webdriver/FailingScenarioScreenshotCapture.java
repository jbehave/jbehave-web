package org.jbehave.web.examples.trader.webdriver;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.webdriver.PerScenarioWebDriverSteps;
import org.openqa.selenium.WebDriver;

public class FailingScenarioScreenshotCapture extends PerScenarioWebDriverSteps {

    public FailingScenarioScreenshotCapture(WebDriver driver) {
        super(driver);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterFailingScenario() throws Exception {
        String home = System.getenv("HOME");
        // getDriver().captureScreenshot(home+"/failedScenario.png");
    }

}
