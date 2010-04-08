package org.jbehave.web.runner.waffle.controllers;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.StepdocGenerator;

public class StepdocController extends MenuAwareController {

	private final StepdocGenerator stepdocGenerator;
	private final CandidateSteps[] steps;

	private StepdocContext stepdocContext;

	public StepdocController(Menu menu, StepdocGenerator stepdocGenerator,
			CandidateSteps... steps) {
		super(menu);
		this.stepdocGenerator = stepdocGenerator;
		this.steps = steps;
		this.stepdocContext = new StepdocContext();
	}

	@ActionMethod(asDefault = true)
	public void generate() {
		stepdocContext.clearStepdocs();
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
