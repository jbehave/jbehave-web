package org.jbehave.web.examples.trader.webdriver;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.examples.trader.webdriver.pages.PageFactory;
import org.jbehave.web.selenium.LocalSwingNotifier;
import org.jbehave.web.selenium.WebDriverConfiguration;
import org.jbehave.web.selenium.WebDriverContext;
import org.jbehave.web.selenium.WebDriverFactory;
import org.jbehave.web.selenium.WebDriverFactoryImpl;
import org.jbehave.web.selenium.WebDriverStepMonitor;

import java.util.List;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.HTML;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.IDE_CONSOLE;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.TXT;
import static org.jbehave.core.reporters.StoryReporterBuilder.Format.XML;

public class TraderWebStories extends JUnitStories {

    private WebDriverFactory driverFactory = new WebDriverFactoryImpl();
    private PageFactory pageFactory = new PageFactory(driverFactory);
    private WebDriverContext context = new WebDriverContext();
    private LocalSwingNotifier notifier = new LocalSwingNotifier();

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        return new WebDriverConfiguration()
                .useWebDriverFactory(driverFactory)
                .useWebDriverContext(context)
                .useStepMonitor(new WebDriverStepMonitor(context, new SilentStepMonitor(), notifier))
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryReporterBuilder(new StoryReporterBuilder() {

                    @Override
                    public StoryReporter reporterFor(String storyPath, Format format) {
                        if (format == IDE_CONSOLE) {
                            return new ConsoleOutput() {
                                @Override
                                public void beforeScenario(String scenarioTitle) {
                                    context.setCurrentScenario(scenarioTitle);
                                    super.beforeScenario(scenarioTitle);
                                }

                                @Override
                                public void afterStory(boolean givenStory) {
                                    notifier.close();
                                    super.afterStory(givenStory);
                                }
                            };
                        } else {
                            return super.reporterFor(storyPath, format);
                        }
                    }

                }
                        .withCodeLocation(CodeLocations.codeLocationFromClass(embeddableClass))
                        .withDefaultFormats()
                        .withFormats(IDE_CONSOLE, TXT, HTML, XML));
    }

    @Override
    public List<CandidateSteps> candidateSteps() {
        return new InstanceStepsFactory(configuration(), new TraderWebSteps(pageFactory), new FailingScenarioScreenshotCapture(driverFactory))
                .createCandidateSteps();
    }


    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story"), null);
    }

}
