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

import com.thoughtworks.selenium.Selenium;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SeleniumStepsTest {

    private static final String NL = "\n";

    private final Configuration configuration = new MostUsefulConfiguration();
    private final StoryParser parser = new RegexStoryParser();
    private final StoryRunner runner = new StoryRunner();
    private final Selenium selenium = mock(Selenium.class);

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

    private void runStory(SeleniumSteps steps) throws Throwable {
        String story = "Scenario: A simple web scenario" + NL 
            + "When a test is executed";
        String path = "/path/to/story";
        InjectableStepsFactory factory = new InstanceStepsFactory(configuration, steps);
        List<CandidateSteps> candidateSteps = factory.createCandidateSteps();
        runner.runBeforeOrAfterStories(configuration, candidateSteps, Stage.BEFORE);
        runner.run(configuration, candidateSteps, parser.parseStory(story, path));
        runner.runBeforeOrAfterStories(configuration, candidateSteps, Stage.AFTER);
        verify(selenium).start();
        verify(selenium).close();
        verify(selenium).stop();
    }

    public class MyPerScenarioSteps extends PerScenarioSeleniumSteps {

        public MyPerScenarioSteps() {
            super(SeleniumStepsTest.this.selenium);
        }


        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

    public class MyPerStorySteps extends PerStorySeleniumSteps {

        public MyPerStorySteps() {
            super(SeleniumStepsTest.this.selenium);
        }


        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

    public class MyPerStoriesSteps extends PerStoriesSeleniumSteps {

        public MyPerStoriesSteps() {
            super(SeleniumStepsTest.this.selenium);
        }


        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

}