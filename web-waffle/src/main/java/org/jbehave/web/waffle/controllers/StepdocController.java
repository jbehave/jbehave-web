package org.jbehave.web.waffle.controllers;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.scenario.steps.StepdocGenerator;
import org.jbehave.scenario.steps.Steps;

public class StepdocController extends MenuAwareController {

	private final StepdocGenerator stepdocGenerator;
	private final Steps steps;

	private StepdocContext stepdocContext;

	public StepdocController(Menu menu, StepdocGenerator stepdocGenerator,
			Steps steps) {
		super(menu);
		this.stepdocGenerator = stepdocGenerator;
		this.steps = steps;
		this.stepdocContext = new StepdocContext();
	}

	@ActionMethod(asDefault = true)
	public void generate() {
		stepdocContext.clearStepdocs();
		stepdocContext.addStepdocs(stepdocGenerator.generate(steps.getClass()));
	}

	public StepdocContext getStepdocContext() {
		return stepdocContext;
	}

	public void setStepdocContext(StepdocContext stepdocContext) {
		this.stepdocContext = stepdocContext;
	}

}
