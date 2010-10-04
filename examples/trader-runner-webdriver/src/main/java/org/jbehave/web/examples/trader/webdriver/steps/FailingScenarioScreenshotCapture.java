package org.jbehave.web.examples.trader.webdriver.steps;

import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.selenium.PerScenarioWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class FailingScenarioScreenshotCapture extends PerScenarioWebDriverSteps {

    public FailingScenarioScreenshotCapture(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure() throws Exception {
        WebDriver webDriver = driverProvider.get();
        if (webDriver instanceof TakesScreenshot) {
            byte[] bytes = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            String path = System.getenv("HOME") + "/failed-scenario-" + System.currentTimeMillis() + ".png";
            IOUtils.write(bytes, new FileOutputStream(path));
            System.out.println("Screenshot at: " + path);
        } else {
            System.out.println("Screenshot cannot be taken: driver " + webDriver.getClass().getName() + " does not support screenshooting");
        }
        super.afterScenario();
    }

    @Override
    @AfterScenario(uponOutcome = Outcome.SUCCESS)
    public void afterScenario() throws Exception {
        super.afterScenario();
    }


}
