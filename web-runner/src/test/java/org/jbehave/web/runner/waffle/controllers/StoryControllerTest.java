package org.jbehave.web.runner.waffle.controllers;

import org.codehaus.waffle.menu.Menu;
import org.jbehave.core.MostUsefulStoryConfiguration;
import org.jbehave.core.StoryConfiguration;
import org.jbehave.core.StoryRunner;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.parser.RegexStoryParser;
import org.jbehave.core.parser.StoryParser;
import org.jbehave.core.steps.Steps;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class StoryControllerTest {

	private static final String NL = "\n";

	private final Menu MENU = new Menu();
	private final StoryConfiguration configuration = new MostUsefulStoryConfiguration();
	private final StoryParser parser = new RegexStoryParser();
	private final StoryRunner runner = new StoryRunner();

	@Test
	public void canRunSuccessfulStory(){
		StoryController controller = new StoryController(MENU, configuration, parser, runner, new MySteps());
		String input = "Scenario: A simple test" + NL
						+ NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper";
		String output = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test is executed" + NL
						+ "Then a tester is a happy hopper";
		controller.getStoryContext().setInput(input);
		controller.run();
		assertLinesMatch(output, controller.getStoryContext().getOutput().trim());
		assertEquals(0, controller.getStoryContext().getFailureMessages().size());
		assertEquals("", controller.getStoryContext().getFailureStackTrace());
	}

	@Test
	public void canRunFailingStory(){
		StoryController controller = new StoryController(MENU, configuration, parser, runner, new MySteps());
		String input = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test fails" + NL
						+ "Then a tester is a happy hopper"; 
		String output = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test fails (FAILED)" + NL
						+ "Then a tester is a happy hopper (NOT PERFORMED)"; 
		controller.getStoryContext().setInput(input);
		controller.run();
		assertLinesMatch(output, controller.getStoryContext().getOutput().trim());
		assertTrue(controller.getStoryContext().getFailureMessages().contains("Test failed"));
		assertTrue(controller.getStoryContext().getFailureStackTrace().contains("java.lang.RuntimeException: Test failed"));
	}

    @Test
    public void canChangeStoryContextMethod(){
        StoryController controller = new StoryController(MENU, configuration, parser, runner, new MySteps());
        StoryContext storyContext = controller.getStoryContext();
        List<String> methods = storyContext.getMethods();
        assertEquals(methods.get(0), storyContext.getMethod());
        storyContext.setMethod(methods.get(1));
        controller.show();
        assertEquals(methods.get(1), storyContext.getMethod());
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
