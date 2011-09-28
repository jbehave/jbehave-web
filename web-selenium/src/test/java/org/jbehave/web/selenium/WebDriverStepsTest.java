package org.jbehave.web.selenium;

import java.util.List;

import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.core.steps.StepCollector.Stage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WebDriverStepsTest {

    private static final String NL = "\n";

    private final Configuration configuration = new MostUsefulConfiguration();
    private final StoryParser parser = new RegexStoryParser();
    private final StoryRunner runner = new StoryRunner();
    private final WebDriver driver = mock(WebDriver.class);
    private final WebDriverProvider driverProvider = new WebDriverProvider() {
        public WebDriver get() {
            return driver;
        }

        public void initialize() {
        }

        public void end() {
            driver.quit();
        }

        public boolean saveScreenshotTo(String path) {
            return false;
        }
    };

    @Test
    public void canInitializeAndQuitWebDriverBeforeAndAfterScenario() throws Throwable {
        runStory(new MyPerScenarioSteps());
    }

    @Test
    public void canInitializeAndQuitWebDriverBeforeAndAfterStory() throws Throwable {
        runStory(new MyPerStorySteps());
    }

    @Test
    public void canInitializeAndQuitWebDriverBeforeAndAfterStories() throws Throwable {
        runStory(new MyPerStoriesSteps());
    }

    private void runStory(WebDriverSteps steps) throws Throwable {
        String story = "Scenario: A simple web scenario" + NL 
            + "When a test is executed";
        String path = "/path/to/story";
        InjectableStepsFactory factory = new InstanceStepsFactory(configuration, steps);
        List<CandidateSteps> candidateSteps = factory.createCandidateSteps();
        runner.runBeforeOrAfterStories(configuration, candidateSteps, Stage.BEFORE);
        runner.run(configuration, candidateSteps, parser.parseStory(story, path));
        runner.runBeforeOrAfterStories(configuration, candidateSteps, Stage.AFTER);
        verify(driver).quit();
    }

    public class MyPerScenarioSteps extends PerScenarioWebDriverSteps {

        public MyPerScenarioSteps() {
            super(WebDriverStepsTest.this.driverProvider);
        }


        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

    public class MyPerStorySteps extends PerStoryWebDriverSteps {

        public MyPerStorySteps() {
            super(WebDriverStepsTest.this.driverProvider);
        }


        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

    public class MyPerStoriesSteps extends PerStoriesWebDriverSteps {

        public MyPerStoriesSteps() {
            super(WebDriverStepsTest.this.driverProvider);
        }


        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

}