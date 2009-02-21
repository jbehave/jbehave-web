package org.jbehave.web.selenium;

import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

public class SeleniumStepsTest {

	private Mockery mockery = new Mockery();

	private static final String NL = "\n";

	private final Configuration configuration = new MostUsefulConfiguration();
	private final ScenarioParser parser = new PatternScenarioParser();
	private final ScenarioRunner runner = new ScenarioRunner();
	private final Selenium mockedSelenium = mockery.mock(Selenium.class);
	private final ConditionRunner mockRunner = mockery
			.mock(ConditionRunner.class);

	@Test
	public void canRunSuccessfulScenario() throws Throwable{
		String scenarioInput = "Scenario: A simple web test" + NL 
						+ NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper"; 
		mockery.checking(new Expectations(){{
			one(mockedSelenium).start();
			one(mockedSelenium).close();
			one(mockedSelenium).stop();
		}});
		Steps steps = new MySteps(){

			@Override
			protected ConditionRunner createConditionRunner(Selenium selenium) {
				return mockRunner;
			}

			@Override
			protected Selenium createSelenium() {
				return mockedSelenium;
			}
			
		};
		runner.run(parser.defineStoryFrom(scenarioInput), configuration, steps);
	}

	public static class MySteps extends SeleniumSteps {
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
