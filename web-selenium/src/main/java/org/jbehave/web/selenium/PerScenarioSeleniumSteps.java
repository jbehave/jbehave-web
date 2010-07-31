package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;

import com.thoughtworks.selenium.Selenium;

/**
 * Steps implementation that can be used in Selenium-based stories. It provides
 * annotated methods to start and stop Selenium before and after scenarios.
 * 
 * @author Mauro Talevi
 */
public abstract class PerScenarioSeleniumSteps extends SeleniumSteps {

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
