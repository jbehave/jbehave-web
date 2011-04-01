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

    public SauceContextOutput(WebDriverProvider webDriverProvider) {
        super("SAUCE_CONTEXT");
        this.webDriverProvider = webDriverProvider;
    }

    @Override
    public StoryReporter createStoryReporter(FilePrintStreamFactory filePrintStreamFactory,
            StoryReporterBuilder storyReporterBuilder) {
        return new SauceContextStoryReporter(webDriverProvider);
    }
}
