package org.jbehave.web.selenium;

import java.util.Arrays;

import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.Embedder.RunningStoriesFailed;
import org.jbehave.core.embedder.PrintStreamEmbedderMonitor;
import org.jbehave.core.embedder.SilentEmbedderMonitor;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.embedder.StoryMapper;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Test;

public class RemoteWebDriverProviderTest {

    @Test(expected = RunningStoriesFailed.class)
    public void shouldFailUponInitialiseWhenRunningWithPerStoriesSteps() throws Throwable {
        // TODO
        runStory(new MyPerStoriesSteps(new RemoteWebDriverProvider())); // will fail because URL is not set
    }

    @Test(expected = RunningStoriesFailed.class)
    public void shouldFailUponInitialiseWhenRunningWithPerStorySteps() throws Throwable {
        runStory(new MyPerStorySteps(new RemoteWebDriverProvider())); // will fail because URL is not set
    }

    private void runStory(WebDriverSteps steps) {
        final String story = "Scenario: A simple web scenario\n" 
                           + "When a test is executed\n";
        String storyPath = "/path/to/story";
        StoryLoader storyLoader = new StoryLoader() {

            public String loadStoryAsText(String storyPath) {
                return story;
            }

        };
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStoryLoader(storyLoader)
                .useStoryReporterBuilder(new StoryReporterBuilder().withFormats(new Format[0]))
                .useStoryControls(new StoryControls().doResetStateBeforeScenario(false));
        Embedder embedder = new Embedder(new StoryMapper(), new StoryRunner(), new SilentEmbedderMonitor(System.out));
        embedder.useConfiguration(configuration);
        embedder.useCandidateSteps(new InstanceStepsFactory(configuration, steps).createCandidateSteps());
        embedder.runStoriesAsPaths(Arrays.asList(storyPath));
    }

    public class MyPerStoriesSteps extends PerStoriesWebDriverSteps {

        public MyPerStoriesSteps(WebDriverProvider driverProvider) {
            super(driverProvider);
        }

        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

    public class MyPerStorySteps extends PerStoryWebDriverSteps {

        public MyPerStorySteps(WebDriverProvider driverProvider) {
            super(driverProvider);
        }

        @When("a test is executed")
        public void aTestIsExecuted() {
        }

    };

}