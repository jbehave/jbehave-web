package org.jbehave.web.examples.flash;

import java.util.List;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.examples.flash.pages.Colors;
import org.jbehave.web.examples.flash.steps.ColorSteps;
import org.jbehave.web.selenium.FlashWebDriverProvider;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.WebDriverProvider;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

public class FlashStories extends JUnitStories {

    private WebDriverProvider driverProvider = new FlashWebDriverProvider("coloredSquare");
    private Colors colorsPage = new Colors(driverProvider);

    @Override
    public Configuration configuration() {
        driverProvider.initialize();
        Class<? extends Embeddable> embeddableClass = this.getClass();
        return new SeleniumConfiguration()
                .useWebDriverProvider(driverProvider)
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder().withCodeLocation(codeLocationFromClass(embeddableClass))
                                .withDefaultFormats().withFormats(CONSOLE, TXT, HTML, XML));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new ColorSteps(colorsPage));
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story"), null);
    }

}
