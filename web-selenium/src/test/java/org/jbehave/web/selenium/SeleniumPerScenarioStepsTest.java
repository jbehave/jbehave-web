package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import org.jbehave.core.MostUsefulStoryConfiguration;
import org.jbehave.core.StoryConfiguration;
import org.jbehave.core.StoryRunner;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.Story;
import org.jbehave.core.parser.RegexStoryParser;
import org.jbehave.core.parser.StoryParser;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.Steps;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static java.util.Arrays.asList;

public class SeleniumPerScenarioStepsTest {

	private Mockery mockery = new Mockery();

	private static final String NL = "\n";

	private final StoryConfiguration configuration = new MostUsefulStoryConfiguration();
	private final StoryParser parser = new RegexStoryParser();
	private final StoryRunner runner = new StoryRunner();
	private final Selenium selenium = mockery.mock(Selenium.class);
	private final ConditionRunner conditionRunner = mockery
			.mock(ConditionRunner.class);

	@Test
	public void canRunSuccessfulStory() throws Throwable{
		String input = "Story: A simple web test" + NL
						+ NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper";
        String path = "/path/to/input";
		mockery.checking(new Expectations(){{
			exactly(3).of(selenium).setContext(with(any(String.class)));
			one(selenium).start();
			one(selenium).close();
			one(selenium).stop();
		}});
		CandidateSteps steps = new MySteps(selenium){
			@Override
			protected ConditionRunner createConditionRunner(Selenium selenium) {
				return conditionRunner;
			}

			@Override
			protected Selenium createSelenium() {
				return selenium;
			}
			
		};
        runner.run(configuration, asList(steps), parser.parseStory(input, path), true);
	}


	public static class MySteps extends SeleniumPerStorySteps {

        public MySteps() {
        }

        public MySteps(Selenium selenium) {
            super(selenium);
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

		@When("a wait is requested")
		public void aWaitIsRequested() {
			waitFor(1);
		}

		@Then("a tester is a happy hopper")
		public void aTesterIsHappy() {
		}
	};

}
