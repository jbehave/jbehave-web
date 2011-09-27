package org.jbehave.web.selenium;

public class SeleniumContext {
	
	private ThreadLocal<String> currentScenario = new ThreadLocal<String>();

    public SeleniumContext() {
        System.out.println();
    }

    public String getCurrentScenario() {
		return currentScenario.get();
	}

	public void setCurrentScenario(String currentScenario) {
		this.currentScenario.set(currentScenario);
	}

}
