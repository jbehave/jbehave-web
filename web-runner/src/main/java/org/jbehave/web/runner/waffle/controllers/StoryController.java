package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.configuration.PropertyBasedStoryConfiguration;
import org.jbehave.core.configuration.StoryConfiguration;
import org.jbehave.core.model.Keywords;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.runner.StoryRunner;
import org.jbehave.core.steps.CandidateSteps;

public class StoryController extends MenuAwareController {

	private final StoryParser storyParser;
	private final StoryRunner storyRunner;
	private final List<CandidateSteps> steps;

	private ByteArrayOutputStream outputStream;
	private StoryConfiguration configuration;
	private StoryContext storyContext;

    public StoryController(Menu menu, StoryConfiguration configuration,
			StoryParser storyParser, StoryRunner storyRunner,
			CandidateSteps... steps) {
		super(menu);
		this.storyParser = storyParser;
		this.storyRunner = storyRunner;
		this.steps = asList(steps);
		this.outputStream = new ByteArrayOutputStream();
        final Properties outputPatterns = new Properties();
        outputPatterns.setProperty("beforeStory", "{0}\n");
        final Keywords keywords = configuration.keywords();
        final boolean reportFailureTrace = false;
		this.configuration = new PropertyBasedStoryConfiguration(configuration) {
			@Override
			public StoryReporter storyReporter() {
				return new TxtOutput(new PrintStream(
						outputStream), outputPatterns, keywords, reportFailureTrace);
			}
		};
		this.storyContext = new StoryContext();
	}

    @ActionMethod(asDefault = true)
	public void show() {
		// no-op
	}

	@ActionMethod(asDefault = false)
	@PRG(false)
	public void run() {
		if (isNotBlank(storyContext.getInput())) {
			try {
				outputStream.reset();
				storyContext.clearFailureCause();
                storyRunner.run(configuration, steps, storyParser.parseStory(storyContext.getInput()));
			} catch (Throwable e) {
				storyContext.runFailedFor(e);
			}
			storyContext.setOutput(outputStream.toString());
		}
	}

    public StoryContext getStoryContext() {
		return storyContext;
	}

	public void setStoryContext(StoryContext storyContext) {
		this.storyContext = storyContext;
	}

}
