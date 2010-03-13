package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.BeforeScenario;
import org.jbehave.scenario.steps.StepsConfiguration;

public abstract class SeleniumPerScenarioSteps extends SeleniumSteps {

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
