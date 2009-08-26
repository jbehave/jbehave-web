package org.jbehave.web.selenium;

import org.jbehave.scenario.steps.SilentStepMonitor;
import org.jbehave.scenario.steps.StepsConfiguration;

import com.thoughtworks.selenium.Selenium;

public class SeleniumStepsConfiguration extends StepsConfiguration {

	public SeleniumStepsConfiguration(final Selenium selenium, final SeleniumContext seleniumContext) {
		useMonitor(new SilentStepMonitor(){
			@Override
			public void performing(String step){
				String context = seleniumContext.getCurrentScenario() + "<br>" + step;
				selenium.setContext(context);
			}
		});
	}

}
