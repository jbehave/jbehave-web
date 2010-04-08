package org.jbehave.web.runner.waffle.controllers;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.PropertyBasedStoryConfiguration;
import org.jbehave.core.StoryConfiguration;
import org.jbehave.core.StoryRunner;
import org.jbehave.core.model.KeyWords;
import org.jbehave.core.parser.StoryParser;
import org.jbehave.core.reporters.PrintStreamStoryReporter;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.steps.CandidateSteps;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class ScenarioController extends MenuAwareController {

	private final StoryParser scenarioParser;
	private final StoryRunner scenarioRunner;
	private final CandidateSteps[] steps;

	private ByteArrayOutputStream outputStream;
	private StoryConfiguration configuration;
	private ScenarioContext scenarioContext;
	
	public ScenarioController(Menu menu, StoryConfiguration configuration,
			StoryParser scenarioParser, StoryRunner scenarioRunner,
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
		this.configuration = new PropertyBasedStoryConfiguration(configuration) {
			@Override
			public StoryReporter storyReporter() {
				return new PrintStreamStoryReporter(new PrintStream(
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
                scenarioRunner.run(configuration, scenarioParser.parseStory(scenarioContext.getInput()), steps);
			} catch (Throwable e) {
				scenarioContext.runFailedFor(e);
			}
			scenarioContext.setOutput(outputStream.toString());
		}
	}

    public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}

	public void setScenarioContext(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}

}
