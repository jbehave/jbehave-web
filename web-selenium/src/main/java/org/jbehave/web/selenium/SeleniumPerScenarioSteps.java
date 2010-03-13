package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.BeforeScenario;
import org.jbehave.scenario.steps.StepsConfiguration;

/**
 * Steps implementation that can be used in Selenium-based scenarios. It
 * provides annotated methods to start and stop Selenium before and after
 * scenarios. It also provides defaults for Selenium and ConditionRunner
 * dependencies, which may be overridden by user when providing the
 * implementation of scenario steps.
 *
 * @author Mauro Talevi
 */
public abstract class SeleniumPerScenarioSteps extends AbstractSeleniumSteps {

    public SeleniumPerScenarioSteps() {
    }

    public SeleniumPerScenarioSteps(StepsConfiguration configuration) {
        super(configuration);
    }

    public SeleniumPerScenarioSteps(Selenium selenium) {
        super(selenium);
    }

    public SeleniumPerScenarioSteps(SeleniumStepsConfiguration configuration) {
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
