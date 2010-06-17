package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.steps.CandidateSteps;

public class StoryController extends MenuAwareController {

	private final StoryRunner storyRunner;
	private final Configuration configuration;
	private final List<CandidateSteps> steps;

	private ByteArrayOutputStream outputStream;
	private StoryContext storyContext;

	public StoryController(Menu menu, StoryRunner storyRunner,
			Configuration configuration, CandidateSteps... steps) {
		super(menu);
		this.storyRunner = storyRunner;
		this.steps = asList(steps);
		this.outputStream = new ByteArrayOutputStream();
		this.configuration = withOutputTo(configuration, this.outputStream);
		this.storyContext = new StoryContext();
	}

	private Configuration withOutputTo(Configuration configuration,
			OutputStream ouputStream) {
		final Properties outputPatterns = new Properties();
		outputPatterns.setProperty("beforeStory", "{0}\n");
		final Keywords keywords = configuration.keywords();
		final boolean reportFailureTrace = false;
		return configuration.useDefaultStoryReporter(new TxtOutput(
				new PrintStream(outputStream), outputPatterns, keywords,
				reportFailureTrace));
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
				StoryParser storyParser = configuration.storyParser();
				storyRunner.run(configuration, steps, storyParser
						.parseStory(storyContext.getInput()));
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
