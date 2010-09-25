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
import org.jbehave.web.webdriver.LocalSwingNotifier;
import org.jbehave.web.webdriver.WebDriverConfiguration;
import org.jbehave.web.webdriver.WebDriverContext;
import org.jbehave.web.webdriver.WebDriverFactory;
import org.jbehave.web.webdriver.WebDriverFactoryImpl;
import org.jbehave.web.webdriver.WebDriverStepMonitor;

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
    private WebDriverContext webDriverContext = new WebDriverContext();

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        return new WebDriverConfiguration()
            .useWebDriverFactory(driverFactory)
            .useWebDriverContext(webDriverContext)
            .useStepMonitor(new WebDriverStepMonitor(driverFactory, webDriverContext, new SilentStepMonitor(), new LocalSwingNotifier()))
            .useStoryLoader(new LoadFromClasspath(embeddableClass))
            .useStoryReporterBuilder(new StoryReporterBuilder(){

                    @Override
                    public StoryReporter reporterFor(String storyPath, Format format) {
                        if ( format == IDE_CONSOLE ){
                            return new ConsoleOutput(){
                                @Override
                                public void beforeScenario(String title) {
                                    webDriverContext.setCurrentScenario(title);
                                    super.beforeScenario(title);
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
