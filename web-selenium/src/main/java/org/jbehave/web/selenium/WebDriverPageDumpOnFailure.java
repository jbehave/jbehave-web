package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.io.File;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * WebDriverSteps that save a page dump upon failure in a scenario outcome.
 */
public class WebDriverPageDumpOnFailure extends WebDriverSteps {

    public static final String DEFAULT_PAGE_DUMP_PATH_PATTERN = "{0}/screenshots/failed-scenario-{1}.html";
    protected final StoryReporterBuilder reporterBuilder;
    protected final String pageDumpPathPattern;

    public WebDriverPageDumpOnFailure(WebDriverProvider driverProvider) {
        this(driverProvider, new StoryReporterBuilder());
    }

    public WebDriverPageDumpOnFailure(WebDriverProvider driverProvider, StoryReporterBuilder reporterBuilder) {
        this(driverProvider, reporterBuilder, DEFAULT_PAGE_DUMP_PATH_PATTERN);
    }

    public WebDriverPageDumpOnFailure(WebDriverProvider driverProvider, StoryReporterBuilder reporterBuilder, String pageDumpPathPattern) {
        super(driverProvider);
        this.reporterBuilder = reporterBuilder;
        this.pageDumpPathPattern = pageDumpPathPattern;
    }

    @AfterScenario(uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) throws Exception {
        if (uuidWrappedFailure instanceof PendingStepFound) {
            return; // we don't do pag dumps for Pending Steps
        }
        String pageDumpPath = pageDumpPath(uuidWrappedFailure.getUUID());
        String currentUrl = "[unknown page title]";
        try {
            currentUrl = driverProvider.get().getCurrentUrl();
        } catch (Exception e) {
        }
        String source = null;
        try {
            source = driverProvider.get().getPageSource();
        } catch (RemoteWebDriverProvider.SauceLabsJobHasEnded e) {
            System.err.println("Dump of page '" + currentUrl + "' has **NOT** been saved. The SauceLabs job has ended, possibly timing out on their end.");
            return;
        } catch (Exception e) {
            System.out.println("Dump of page '" + currentUrl + ". Will try again. Cause: " + e.getMessage());
            try {
                source = driverProvider.get().getPageSource();
            } catch (Exception e1) {
                System.err.println("Dump of page '" + currentUrl + "' has **NOT** been saved to '" + pageDumpPath + "' because error '" + e.getMessage() + "' encountered. Stack trace follows:");
                e.printStackTrace();
                return;
            }
        }
        if (source != null) {
            new File(pageDumpPath).getParentFile().mkdirs();
            FileWriter fw = new FileWriter(pageDumpPath);
            fw.write(source);
            fw.close();
            System.out.println("Dump of page '" + currentUrl + "' has been saved to '" + pageDumpPath +"' with " + new File(pageDumpPath).length() + " bytes");
        } else {
            System.err.println("Dump of page '" + currentUrl + "' has **NOT** been saved. If there is no error, perhaps the WebDriver type you are using is not compatible with taking screenshots");
        }
    }

    protected String pageDumpPath(UUID uuid) {
        return MessageFormat.format(pageDumpPathPattern, reporterBuilder.outputDirectory(), uuid);
    }

}
