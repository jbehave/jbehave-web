package org.jbehave.web.selenium;

import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class SeleniumPerScenarioStepsTest {

	private Mockery mockery = new Mockery();

	private static final String NL = "\n";

	private final Configuration configuration = new MostUsefulConfiguration();
	private final ScenarioParser parser = new PatternScenarioParser();
	private final ScenarioRunner runner = new ScenarioRunner();
	private final Selenium selenium = mockery.mock(Selenium.class);
	private final ConditionRunner conditionRunner = mockery
			.mock(ConditionRunner.class);

	@Test
	public void canRunSuccessfulScenario() throws Throwable{
		String input = "Scenario: A simple web test" + NL
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
		Steps steps = new MySteps(selenium){

			@Override
			protected ConditionRunner createConditionRunner(Selenium selenium) {
				return conditionRunner;
			}

			@Override
			protected Selenium createSelenium() {
				return selenium;
			}
			
		};
        StoryDefinition definition = parser.defineStoryFrom(input, path);
        runner.run(definition, configuration, true, steps);
	}


	public static class MySteps extends SeleniumPerScenarioSteps {

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
