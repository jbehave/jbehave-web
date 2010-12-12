package org.jbehave.web.selenium;

import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;

public class SeleniumContextOutput extends org.jbehave.core.reporters.Format {
        private final SeleniumContext seleniumContext;

        public SeleniumContextOutput(SeleniumContext seleniumContext) {
            super("SELENIUM_CONTEXT");
            this.seleniumContext = seleniumContext;
        }

        @Override
        public StoryReporter makeStoryReporter(FilePrintStreamFactory filePrintStreamFactory, StoryReporterBuilder storyReporterBuilder) {
            return new SeleniumContextStoryReporter(seleniumContext);
        }
    }
