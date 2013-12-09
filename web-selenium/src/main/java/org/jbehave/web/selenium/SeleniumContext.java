package org.jbehave.web.selenium;

import org.jbehave.core.steps.StepContext;

/**
 * @deprecated Use org.jbehave.core.steps.StepContext
 */
public class SeleniumContext extends StepContext {
	
	private ThreadLocal<String> currentScenario = new ThreadLocal<String>();

    public String getCurrentScenario() {
		return currentScenario.get();
	}

	public void setCurrentScenario(String currentScenario) {
		this.currentScenario.set(currentScenario);
	}

}
