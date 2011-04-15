package org.jbehave.web.selenium;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.failures.BeforeOrAfterFailed;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

public class BadlyInitializingWebDriverProviderTest {

    private static final String NL = "\n";

    private final StoryParser parser = new RegexStoryParser();
    private final StoryRunner runner = new StoryRunner();
    private final WebDriverProvider driverProvider = new WebDriverProvider() {
        public WebDriver get() {
            fail("should never do get() if initialize() barfs");
            return null;
        }

        public void initialize() {
            throw new UnsupportedOperationException("hello, this never makes it to the log");

        }

        public boolean saveScreenshotTo(String path) {
            fail("should never do saveScreenshotTo(..) if initialize() barfs");
            return false;
        }

        public String toString() {
            return "TestWDP";
        };
    };

    StringBuilder orderedEvents;

    @Test
    public void shouldFailDueToInitializationFailure() throws Throwable {

        orderedEvents = new StringBuilder();

        String story = "Scenario: A simple web scenario" + NL + "Given a test" + NL + "When a test is executed" + NL
                + "Then a tester is a happy hopper";
        String path = "/path/to/story";
        MySteps steps = new MySteps();
        MyBeforeAfterScenarioSteps myBeforeAfterScenarioSteps = new MyBeforeAfterScenarioSteps();
        MyPerStoryWebDriverSteps myPerStorySteps = new MyPerStoryWebDriverSteps(driverProvider);
        OutputStream out = new ByteArrayOutputStream();
        final TxtOutput reporter = new TxtOutput(new PrintStream(out), new Properties(),
                new LocalizedKeywords(), true);
        StoryReporterBuilder builder = new StoryReporterBuilder(){

            @Override
            public StoryReporter build(String storyPath) {
                return reporter;
            }
            
        };
        Configuration configuration = new MostUsefulConfiguration().useStoryReporterBuilder(builder).useStoryControls(new StoryControls().doResetStateBeforeScenario(false));
        
        InjectableStepsFactory factory = new InstanceStepsFactory(configuration, steps, myPerStorySteps, myBeforeAfterScenarioSteps);
        try {
            runner.run(configuration, factory.createCandidateSteps(), parser.parseStory(story, path));
        } catch (BeforeOrAfterFailed beforeOrAfterFailed) {
            Throwable rootCause = beforeOrAfterFailed.getCause();
            assertNotNull(rootCause);
            assertTrue(rootCause instanceof UnsupportedOperationException);
            assertTrue(rootCause.getMessage().contains("hello"));
        }
        System.out.println(out);

        assertEquals("PerStoryWebDriverSteps.beforeStory()\n"
                // none of the per-scenario steps or scenario steps or afterStory steps should execute
                //+ "PerStoryWebDriverSteps.afterStory()\n"
                , orderedEvents.toString());
    }

    public class MyPerStoryWebDriverSteps extends PerStoryWebDriverSteps {

        public MyPerStoryWebDriverSteps(WebDriverProvider driverProvider) {
            super(driverProvider);
        }

        @Override
        @BeforeStory
        public void beforeStory() throws Exception {
            orderedEvents.append("PerStoryWebDriverSteps.beforeStory()\n");
            super.beforeStory();
        }

        @Override
        @AfterStory
        public void afterStory() throws Exception {
            orderedEvents.append("PerStoryWebDriverSteps.afterStory()\n");
            super.afterStory();
        }
    };

    public class MyBeforeAfterScenarioSteps {

        @BeforeScenario
        public void beforeScenario() throws Exception {
            orderedEvents.append("MyBeforeAfterScenarioSteps.beforeScenario()\n");
        }

        @BeforeScenario
        public void afterScenario() throws Exception {
            orderedEvents.append("MyBeforeAfterScenarioSteps.afterScenario()\n");
        }
    }

    public class MySteps {

        @Given("a test")
        public void aTest() {
            orderedEvents.append("MySteps.aTest()\n");
        }

        @When("a test is executed")
        public void aTestIsExecuted() {
            orderedEvents.append("MySteps.aTestIsExecuted()\n");
        }

        @When("a test fails")
        public void aTestFails() {
            orderedEvents.append("MySteps.aTestFails()\n");
            throw new RuntimeException("Test failed");
        }

        @Then("a tester is a happy hopper")
        public void aTesterIsHappy() {
            orderedEvents.append("MySteps.aTesterIsHappy()\n");
        }
    }

}
