package org.jbehave.web.selenium;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.selenium.PerStoryWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProxy;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PerStoryWebDriverStepsTest {

    private static final String NL = "\n";

    private final Configuration configuration = new MostUsefulConfiguration();
    private final StoryParser parser = new RegexStoryParser();
    private final StoryRunner runner = new StoryRunner();
    private final WebDriver driver = mock(WebDriver.class);
	private final WebDriver proxyDriver = new WebDriverProxy();

    @Ignore    
    @Test
    public void canRunSuccessfulStory() throws Throwable {
        String story = "Scenario: A simple web scenario" + NL 
            + "Given a test" + NL 
            + "When a test is executed" + NL
            + "Then a tester is a happy hopper";
        String path = "/path/to/story";
        MySteps steps = new MySteps();
        InjectableStepsFactory factory = new InstanceStepsFactory(configuration, steps);
        runner.run(configuration, factory.createCandidateSteps(), parser.parseStory(story, path));
        verify(driver).close();
    }

    public class MySteps extends PerStoryWebDriverSteps {

        public MySteps() {
            super(proxyDriver);
        }

        @Override
        public void beforeStory() throws Exception {
            setDriver(driver);
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