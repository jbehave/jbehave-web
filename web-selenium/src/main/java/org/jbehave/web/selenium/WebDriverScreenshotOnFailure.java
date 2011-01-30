package org.jbehave.web.selenium;

import java.text.MessageFormat;
import java.util.UUID;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;

/**
 * WebDriverSteps that save screenshot upon failure in a scenario outcome.
 * Not all WebDriver implementations support the screenshot capability
 */
public class WebDriverScreenshotOnFailure extends WebDriverSteps {

    public static final String DEFAULT_SCREENSHOT_PATH_PATTERN = "{0}/screenshots/failed-scenario-{1}.png";
    protected final StoryReporterBuilder reporterBuilder;
    protected final String screenshotPathPattern;

    public WebDriverScreenshotOnFailure(WebDriverProvider driverProvider) {
        this(driverProvider, new StoryReporterBuilder());
    }

    public WebDriverScreenshotOnFailure(WebDriverProvider driverProvider, StoryReporterBuilder reporterBuilder) {
        this(driverProvider, reporterBuilder, DEFAULT_SCREENSHOT_PATH_PATTERN);
    }

    public WebDriverScreenshotOnFailure(WebDriverProvider driverProvider, StoryReporterBuilder reporterBuilder, String screenshotPathPattern) {
        super(driverProvider);
        this.reporterBuilder = reporterBuilder;
        this.screenshotPathPattern = screenshotPathPattern;
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) throws Exception {
        String screenshotPath = screenshotPath(uuidWrappedFailure.getUUID());
        if ( driverProvider.saveScreenshotTo(screenshotPath) ) {
            System.out.println("Screenshot has been saved to '" + screenshotPath +"'");
        } else {
            System.out.println(driverProvider.get().getClass().getName() + " does not support taking screenshots.");
        }
    }

    protected String screenshotPath(UUID uuid) {
        return MessageFormat.format(screenshotPathPattern, reporterBuilder.outputDirectory(), uuid);
    }

}
