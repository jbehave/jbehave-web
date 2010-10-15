package org.jbehave.web.examples.trader.webdriver.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.selenium.PerScenarioWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProvider;

public class FailingScenarioScreenshotCapture extends PerScenarioWebDriverSteps {

    public FailingScenarioScreenshotCapture(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure() throws Exception {
        String screenshotPath = "target/screenshots/failed-scenario-" + System.currentTimeMillis() + ".png";
        driverProvider.saveScreenshotTo(screenshotPath);        
        super.afterScenario();
    }

    @Override
    @AfterScenario(uponOutcome = Outcome.SUCCESS)
    public void afterScenario() throws Exception {
        super.afterScenario();
    }


}
