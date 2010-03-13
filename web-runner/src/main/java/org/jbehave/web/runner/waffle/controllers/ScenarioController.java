package org.jbehave.web.runner.waffle.controllers;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.ScenarioRunner;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.parser.ScenarioParser;
import org.jbehave.scenario.reporters.PrintStreamScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.CandidateSteps;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class ScenarioController extends MenuAwareController {

	private final ScenarioParser scenarioParser;
	private final ScenarioRunner scenarioRunner;
	private final CandidateSteps[] steps;

	private ByteArrayOutputStream outputStream;
	private Configuration configuration;
	private ScenarioContext scenarioContext;
	
	public ScenarioController(Menu menu, Configuration configuration,
			ScenarioParser scenarioParser, ScenarioRunner scenarioRunner,
			CandidateSteps... steps) {
		super(menu);
		this.scenarioParser = scenarioParser;
		this.scenarioRunner = scenarioRunner;
		this.steps = steps;
		this.outputStream = new ByteArrayOutputStream();
        final Properties properties = new Properties();
        properties.setProperty("beforeStory", "{0}\n");
        final KeyWords keywords = configuration.keywords();
        final boolean reportErrors = false;
		this.configuration = new PropertyBasedConfiguration(configuration) {
			@Override
			public ScenarioReporter forReportingScenarios() {
				return new PrintStreamScenarioReporter(new PrintStream(
						outputStream), properties, keywords, reportErrors);
			}
		};
		this.scenarioContext = new ScenarioContext();
	}
	
	@ActionMethod(asDefault = true)
	public void show() {
		// no-op
	}

	@ActionMethod(asDefault = false)
	@PRG(false)
	public void run() {
		if (isNotBlank(scenarioContext.getInput())) {
			try {
				outputStream.reset();
				scenarioContext.clearFailureCause();
				scenarioRunner.run(storyDefinition(), configuration, true, steps);
			} catch (Throwable e) {
				scenarioContext.runFailedFor(e);
			}
			scenarioContext.setOutput(outputStream.toString());
		}
	}

	private StoryDefinition storyDefinition() {
		return scenarioParser.defineStoryFrom(scenarioContext.getInput());
	}

	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}

	public void setScenarioContext(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

}
