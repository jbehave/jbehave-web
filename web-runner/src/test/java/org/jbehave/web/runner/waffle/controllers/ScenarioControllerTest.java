package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.codehaus.waffle.menu.Menu;
import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.MostUsefulConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.web.runner.waffle.controllers.ScenarioController;
import org.junit.Test;


public class ScenarioControllerTest {

	private static final String NL = "\n";

	private final Menu MENU = new Menu();
	private final Configuration configuration = new MostUsefulConfiguration();
	private final ScenarioParser parser = new PatternScenarioParser();
	private final ScenarioRunner runner = new ScenarioRunner();

	@Test
	public void canRunSuccessfulScenario(){
		ScenarioController controller = new ScenarioController(MENU, configuration, parser, runner, new MySteps());
		String scenarioInput = "Scenario: A simple test" + NL
						+ NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper";
		String scenarioOutput = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper";
		controller.getScenarioContext().setInput(scenarioInput);
		controller.run();
		assertLinesMatch(scenarioOutput, controller.getScenarioContext().getOutput().trim());
		assertEquals(0, controller.getScenarioContext().getFailureMessages().size());
		assertEquals("", controller.getScenarioContext().getFailureStackTrace());
	}

	@Test
	public void canRunFailingScenario(){
		ScenarioController controller = new ScenarioController(MENU, configuration, parser, runner, new MySteps());
		String scenarioInput = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test fails" + NL
						+ "Then a tester is a happy hopper"; 
		String scenarioOutput = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test fails (FAILED)" + NL
						+ "Then a tester is a happy hopper (NOT PERFORMED)"; 
		controller.getScenarioContext().setInput(scenarioInput);
		controller.run();
		assertLinesMatch(scenarioOutput, controller.getScenarioContext().getOutput().trim());
		assertTrue(controller.getScenarioContext().getFailureMessages().contains("Test failed"));
		assertTrue(controller.getScenarioContext().getFailureStackTrace().contains("java.lang.RuntimeException: Test failed"));
	}

    @Test
    public void canChangeScenarioContextMethod(){
        ScenarioController controller = new ScenarioController(MENU, configuration, parser, runner, new MySteps());
        ScenarioContext scenarioContext = controller.getScenarioContext();
        List<String> methods = scenarioContext.getMethods();
        assertEquals(methods.get(0), scenarioContext.getMethod());
        scenarioContext.setMethod(methods.get(1));
        controller.show();
        assertEquals(methods.get(1), scenarioContext.getMethod());
    }	
	
	private void assertLinesMatch(String expected, String actual) {
		String[] expectedLines = expected.split(NL);
		String[] actualLines = actual.split(NL);
		assertEquals(expectedLines.length, actualLines.length);
		for (int i = 0; i < expectedLines.length; i++ ){ 
			assertEquals(expectedLines[i].trim(), actualLines[i].trim());
		}
		
	}

	public static class MySteps extends Steps {
		@Given("a test")
		public void aTest(){
		}
		@When("a test is executed")
		public void aTestIsExecuted(){
		}
		@When("a test fails")
		public void aTestFails(){
			throw new RuntimeException("Test failed");
		}
		@Then("a tester is a happy hopper")
		public void aTesterIsHappy(){
		}
	};

}
