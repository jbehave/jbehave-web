package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepFinder;
import org.jbehave.core.steps.Stepdoc;
import org.jbehave.web.runner.context.StepdocContext;

public class StepdocController extends MenuAwareController {

	private final List<CandidateSteps> steps;
	private final StepFinder stepFinder;

	private StepdocContext stepdocContext;
	private List<Stepdoc> stepdocs;

	public StepdocController(Menu menu, Configuration configuration,
			CandidateSteps... steps) {
		super(menu);
		this.steps = asList(steps);
		this.stepFinder = configuration.stepFinder();
		this.stepdocs = stepFinder.stepdocs(this.steps);
		this.stepdocContext = new StepdocContext();
		this.stepdocContext.addStepsInstances(stepFinder.stepsInstances(this.steps));
	}

	@ActionMethod(asDefault = true)
	public void find() {
		stepdocContext.clearStepdocs();
		String matchingStep = stepdocContext.getMatchingStep();
		if (isNotBlank(matchingStep)) {
			stepdocContext.addStepdocs(stepFinder.findMatching(matchingStep, steps));
		} else {
			stepdocContext.addStepdocs(stepdocs);
		}
	}

	@ActionMethod
	public void toggle() {
		// used to toggle context view
	}

	public StepdocContext getStepdocContext() {
		return stepdocContext;
	}

	public void setStepdocContext(StepdocContext stepdocContext) {
		this.stepdocContext = stepdocContext;
	}

}
