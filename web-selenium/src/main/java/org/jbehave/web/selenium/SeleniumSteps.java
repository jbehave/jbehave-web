package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.steps.StepsConfiguration;

/**
 * Steps implementation that can be used in Selenium-based scenarios. It
 * provides annotated methods to start and stop Selenium before and after
 * scenarios. It also provides defaults for Selenium and ConditionRunner
 * dependencies, which may be overridden by user when providing the
 * implementation of scenario steps.
 * 
 * @author Mauro Talevi
 * @deprecated ... use SeleniumPerScenarioSteps or SeleniumPerStorySteps instead
 */
public abstract class SeleniumSteps extends AbstractSeleniumSteps {

    protected SeleniumSteps() {
    }

    protected SeleniumSteps(StepsConfiguration configuration) {
        super(configuration);
    }

    protected SeleniumSteps(Selenium selenium) {
        super(selenium);
    }

    protected SeleniumSteps(SeleniumStepsConfiguration configuration) {
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
