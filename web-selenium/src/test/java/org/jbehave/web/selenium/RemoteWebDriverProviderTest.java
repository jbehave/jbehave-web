package org.jbehave.web.selenium;

import java.util.Arrays;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.Embedder.RunningStoriesFailed;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Test;

public class RemoteWebDriverProviderTest {

    @Test(expected = RunningStoriesFailed.class)
    public void shouldFailUponInitialiseWhenRunningWithPerStoriesWebDriverSteps() throws Throwable {
        runStory(new MyPerStoriesWebDriverSteps(new RemoteWebDriverProvider())); // will fail because URL is not set
    }

    @Test(expected = RunningStoriesFailed.class)
    public void shouldFailUponInitialiseWhenRunningWithPerStoryWebDriverSteps() throws Throwable {
        runStory(new MyPerStoryWebDriverSteps(new RemoteWebDriverProvider())); // will fail because URL is not set
    }

    private void runStory(WebDriverSteps steps) {
        final String story = "Scenario: A simple web scenario\n" + "Given a test\n" + "When a test is executed\n"
                + "Then a tester is a happy hopper";
        String storyPath = "/path/to/story";
        StoryLoader storyLoader = new StoryLoader() {

            public String loadStoryAsText(String storyPath) {
                return story;
            }

        };
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStoryLoader(storyLoader)
                .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats())
                .useStoryControls(new StoryControls().doResetStateBeforeScenario(false));
        Embedder embedder = new Embedder();
        embedder.useConfiguration(configuration);
        embedder.useCandidateSteps(new InstanceStepsFactory(configuration, steps).createCandidateSteps());
        embedder.runStoriesAsPaths(Arrays.asList(storyPath));
    }

    public class MyPerStoriesWebDriverSteps extends PerStoriesWebDriverSteps {

        public MyPerStoriesWebDriverSteps(WebDriverProvider driverProvider) {
            super(driverProvider);
        }

        @Given("a test")
        public void aTest() {
        }

        @When("a test is executed")
        public void aTestIsExecuted() {
        }

        @When("a test fails")
        public void aTestFails() {
            throw new RuntimeException("Test failed");
        }

        @Then("a tester is a happy hopper")
        public void aTesterIsHappy() {
        }
    };

    public class MyPerStoryWebDriverSteps extends PerStoryWebDriverSteps {

        public MyPerStoryWebDriverSteps(WebDriverProvider driverProvider) {
            super(driverProvider);
        }

        @Given("a test")
        public void aTest() {
        }

        @When("a test is executed")
        public void aTestIsExecuted() {
        }

        @When("a test fails")
        public void aTestFails() {
            throw new RuntimeException("Test failed");
        }

        @Then("a tester is a happy hopper")
        public void aTesterIsHappy() {
        }
    };

}