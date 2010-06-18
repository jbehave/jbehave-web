package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepFinder;
import org.jbehave.core.steps.Stepdoc;

public class StepdocController extends MenuAwareController {

	private final Configuration configuration;
	private final List<CandidateSteps> steps;

	private StepdocContext stepdocContext;

	public StepdocController(Menu menu, Configuration configuration,
			CandidateSteps... steps) {
		super(menu);
		this.configuration = configuration;
		this.steps = asList(steps);
		this.stepdocContext = new StepdocContext();
	}

	@ActionMethod(asDefault = true)
	public void find() {
		stepdocContext.clearStepdocs();
		StepFinder stepFinder = configuration.stepFinder();
		List<Stepdoc> stepdocs = new ArrayList<Stepdoc>();
		String matchingStep = stepdocContext.getMatchingStep();
		if (isNotBlank(matchingStep)) {
			stepdocs = stepFinder.findMatching(matchingStep, steps);
		} else {
			stepdocs = stepFinder.stepdocs(steps);
		}
		stepdocContext.addStepdocs(stepdocs);
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
