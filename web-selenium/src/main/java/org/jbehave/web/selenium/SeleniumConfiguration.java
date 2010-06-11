package org.jbehave.web.selenium;

import org.jbehave.core.configuration.StoryConfiguration;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepMonitor;

import com.thoughtworks.selenium.Selenium;

public class SeleniumConfiguration extends StoryConfiguration {

	private final Selenium selenium;

	public SeleniumConfiguration(Selenium selenium, SeleniumContext seleniumContext) {
		this(selenium, seleniumContext, new SilentStepMonitor());
	}

	public SeleniumConfiguration(Selenium selenium, SeleniumContext seleniumContext, StepMonitor stepMonitor) {
		this.selenium = selenium;
		useStepMonitor(new SeleniumStepsMonitor(selenium, seleniumContext, stepMonitor));
	}

	public Selenium getSelenium() {
		return selenium;
	}

}
