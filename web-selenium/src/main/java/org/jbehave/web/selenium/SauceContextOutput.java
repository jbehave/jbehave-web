package org.jbehave.web.selenium;

import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

/**
 * A {@link Format} that uses {@link SauceContextStoryReporter}.
 */
public class SauceContextOutput extends Format {

    private final WebDriverProvider webDriverProvider;
    private final SeleniumContext seleniumContext;

    public SauceContextOutput(WebDriverProvider webDriverProvider, SeleniumContext seleniumContext) {
        super("SAUCE_CONTEXT");
        this.webDriverProvider = webDriverProvider;
        this.seleniumContext = seleniumContext;
    }

    @Override
    public StoryReporter createStoryReporter(FilePrintStreamFactory filePrintStreamFactory,
            StoryReporterBuilder storyReporterBuilder) {
        return new SauceContextStoryReporter(webDriverProvider, seleniumContext);
    }
}
