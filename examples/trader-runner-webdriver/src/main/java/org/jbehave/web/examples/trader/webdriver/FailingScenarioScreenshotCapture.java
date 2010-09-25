package org.jbehave.web.examples.trader.webdriver;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.web.webdriver.PerScenarioWebDriverSteps;
import org.jbehave.web.webdriver.WebDriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.FileOutputStream;

public class FailingScenarioScreenshotCapture extends PerScenarioWebDriverSteps {

    public FailingScenarioScreenshotCapture(WebDriverFactory driverFactory) {
        super(driverFactory);
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure() throws Exception {
        String home = System.getenv("HOME");
        WebDriver webDriver = driverFactory.get();
        if (webDriver instanceof TakesScreenshot) {
            byte[] bytes = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            String locn = home + "/failed-scenario-" + System.currentTimeMillis() + ".png";
            FileOutputStream fos = new FileOutputStream(locn);
            fos.write(bytes);
            fos.close();
            System.out.println("Screenshot at: " + locn);
        } else {
            System.out.println("Screenshot cannot be taken: driver " + webDriver.getClass().getName() + " does not support Screenshotting");
        }
        super.afterScenario();
    }

    @Override
    @AfterScenario(uponOutcome = Outcome.SUCCESS)
    public void afterScenario() throws Exception {
        super.afterScenario();
    }


}
