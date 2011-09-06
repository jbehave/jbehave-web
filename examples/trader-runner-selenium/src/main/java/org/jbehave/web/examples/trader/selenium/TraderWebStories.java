package org.jbehave.web.examples.trader.selenium;

import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.web.examples.trader.selenium.pages.Pages;
import org.jbehave.web.examples.trader.selenium.steps.TraderWebSteps;
import org.jbehave.web.selenium.PerStorySeleniumSteps;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumScreenshotOnFailure;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.jbehave.web.selenium.SeleniumSteps;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

public class TraderWebStories extends JUnitStories {

    private Selenium selenium = SeleniumConfiguration.defaultSelenium();
    private ConditionRunner conditionRunner = SeleniumConfiguration.defaultConditionRunner(selenium);
    private Pages pages = new Pages(selenium, conditionRunner);
    private SeleniumSteps lifecycleSteps = new PerStorySeleniumSteps(selenium);
    private SeleniumContext seleniumContext = new SeleniumContext();

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        return new SeleniumConfiguration()
            .useSelenium(selenium) 
            .useSeleniumContext(seleniumContext)
            .useStepMonitor(new SeleniumStepMonitor(selenium, seleniumContext, new SilentStepMonitor()))
            .useStoryLoader(new LoadFromClasspath(embeddableClass))
            .useStoryReporterBuilder(new StoryReporterBuilder()
                .withCodeLocation(codeLocationFromClass(embeddableClass))
                .withDefaultFormats()
                .withFormats(CONSOLE, TXT, HTML, XML));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(),
                new TraderWebSteps(pages),
                lifecycleSteps,
                new SeleniumScreenshotOnFailure(selenium));
    }
    
    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story"), null);
    }

}
