package org.jbehave.web.selenium;

public class SeleniumContext {
	
	private String currentScenario = "";

	public String getCurrentScenario() {
		return currentScenario;
	}

	public void setCurrentScenario(String currentScenario) {
		this.currentScenario = currentScenario;
	}

}
