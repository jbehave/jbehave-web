package org.jbehave.web.selenium;

public class WebDriverContext {
	
	private String currentScenario = "";

	public String getCurrentScenario() {
		return currentScenario;
	}

	public void setCurrentScenario(String currentScenario) {
		this.currentScenario = currentScenario;
	}

}
