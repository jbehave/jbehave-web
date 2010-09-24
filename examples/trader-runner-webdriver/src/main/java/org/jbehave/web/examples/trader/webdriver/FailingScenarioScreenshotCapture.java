package org.jbehave.web.examples.trader.webdriver;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.webdriver.PerScenarioWebDriverSteps;
import org.jbehave.web.webdriver.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class FailingScenarioScreenshotCapture extends PerScenarioWebDriverSteps {

    public FailingScenarioScreenshotCapture(WebDriverFactory driverFactory) {
        super(driverFactory);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterFailingScenario() throws Exception {
        String home = System.getenv("HOME");
        // ((TakesScreenshot) driverFactory.get()).getScreenshotAs(OutputType.FILE);
        // home+"/failedScenario.png"
    }

}
