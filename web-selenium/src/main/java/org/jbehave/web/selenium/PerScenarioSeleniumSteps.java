package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;

import com.thoughtworks.selenium.Selenium;

/**
 * Steps implementation that can be used before and after lifecycle in
 * Selenium-based stories. It provides annotated methods to start Selenium
 * before each scenario and close/stop Selenum after each scenario.
 * 
 * @author Mauro Talevi
 */
public class PerScenarioSeleniumSteps extends SeleniumSteps {

    public PerScenarioSeleniumSteps() {
        super();
    }

    public PerScenarioSeleniumSteps(Selenium selenium) {
        super(selenium);
    }

    @BeforeScenario
    public void beforeScenario() throws Exception {
        selenium.start();
    }

    @AfterScenario
    public void afterScenario() throws Exception {
        selenium.close();
        selenium.stop();
    }

}
