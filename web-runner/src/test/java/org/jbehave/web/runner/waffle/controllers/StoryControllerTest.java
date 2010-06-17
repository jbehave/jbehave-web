package org.jbehave.web.runner.waffle.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.codehaus.waffle.menu.Menu;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.steps.Steps;
import org.junit.Test;


public class StoryControllerTest {

	private static final String NL = "\n";

	private final Menu MENU = new Menu();
	private final Configuration configuration = new MostUsefulConfiguration();
	private final StoryRunner runner = new StoryRunner();

	@Test
	public void canRunSuccessfulStory(){
		StoryController controller = new StoryController(MENU, runner, configuration, new MySteps());
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
		assertThat(controller.getStoryContext().getFailureMessages().size(), equalTo(0));
		assertThat(controller.getStoryContext().getFailureStackTrace(), equalTo(""));
	}

	@Test
	public void canRunFailingStory(){
		StoryController controller = new StoryController(MENU, runner, configuration, new MySteps());
		String input = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test fails" + NL
						+ "Then a tester is a happy hopper"; 
		String output = "Scenario: A simple test" + NL
						+ "Given a test" + NL
						+ "When a test fails (FAILED)" + NL
						+ "(java.lang.RuntimeException: Test failed)"+ NL
						+ "Then a tester is a happy hopper (NOT PERFORMED)"; 
		controller.getStoryContext().setInput(input);
		controller.run();
		assertLinesMatch(output, controller.getStoryContext().getOutput().trim());
		assertThat(controller.getStoryContext().getFailureMessages().contains("Test failed"), is(true));
		assertThat(controller.getStoryContext().getFailureStackTrace().contains("java.lang.RuntimeException: Test failed"), is(true));
	}

    @Test
    public void canChangeStoryContextMethod(){
        StoryController controller = new StoryController(MENU, runner, configuration, new MySteps());
        StoryContext storyContext = controller.getStoryContext();
        List<String> methods = storyContext.getMethods();
        assertThat(storyContext.getMethod(), equalTo(methods.get(0)));
        storyContext.setMethod(methods.get(1));
        controller.show();
        assertThat(storyContext.getMethod(), equalTo(methods.get(1)));
    }	
	
	private void assertLinesMatch(String expected, String actual) {
		String[] expectedLines = expected.split(NL);
		String[] actualLines = actual.split(NL);
		assertEquals(expectedLines.length, actualLines.length);
		for (int i = 0; i < expectedLines.length; i++ ){ 
			assertThat(actualLines[i].trim(), equalTo(expectedLines[i].trim()));
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
