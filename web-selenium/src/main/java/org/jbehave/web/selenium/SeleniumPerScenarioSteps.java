package org.jbehave.web.selenium;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.configuration.Configuration;

import com.thoughtworks.selenium.Selenium;

/**
 * Steps implementation that can be used in Selenium-based scenarios. It
 * provides annotated methods to start and stop Selenium before and after
 * scenarios. It can also provides defaults for Selenium and ConditionRunner
 * dependencies, which may be overridden by user when providing the
 * implementation of scenario steps.
 *
 * @author Mauro Talevi
 */
public abstract class SeleniumPerScenarioSteps extends AbstractSeleniumSteps {

    public SeleniumPerScenarioSteps() {
    }

    public SeleniumPerScenarioSteps(Configuration configuration) {
        super(configuration);
    }

    public SeleniumPerScenarioSteps(Selenium selenium) {
        super(selenium);
    }

    public SeleniumPerScenarioSteps(SeleniumConfiguration configuration) {
        super(configuration);
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
