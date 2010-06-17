package org.jbehave.web.runner.waffle.controllers;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepdocGenerator;

public class StepdocController extends MenuAwareController {

	private final Configuration configuration;
	private final CandidateSteps[] steps;

	private StepdocContext stepdocContext;

	public StepdocController(Menu menu, Configuration configuration,
			CandidateSteps... steps) {
		super(menu);
		this.configuration = configuration;
		this.steps = steps;
		this.stepdocContext = new StepdocContext();
	}

	@ActionMethod(asDefault = true)
	public void generate() {
		stepdocContext.clearStepdocs();
		StepdocGenerator stepdocGenerator = configuration.stepdocGenerator();
		stepdocContext.addStepdocs(stepdocGenerator.generate(steps));			
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
