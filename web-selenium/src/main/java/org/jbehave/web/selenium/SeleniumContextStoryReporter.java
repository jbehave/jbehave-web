package org.jbehave.web.selenium;

public class SeleniumContextStoryReporter extends org.jbehave.core.reporters.NullStoryReporter {
    private final SeleniumContext seleniumContext;

    public SeleniumContextStoryReporter(SeleniumContext seleniumContext) {
        this.seleniumContext = seleniumContext;
    }

    @Override
    public void beforeScenario(String title, boolean givenStory) {
        seleniumContext.setCurrentScenario(title);
    }
}
