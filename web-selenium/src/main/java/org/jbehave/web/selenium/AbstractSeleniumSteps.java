package org.jbehave.web.selenium;

import java.util.concurrent.TimeUnit;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.JUnitConditionRunner;

/**
 * Steps implementation that can be used in Selenium-based scenarios. It does not start,
 * stop or close Selenium at all.
 * It only provides instances of Selenium and ConditionRunner
 * dependencies, which may be overridden by user when providing the
 * implementation of scenario steps.
 */
public class AbstractSeleniumSteps {

    protected final Selenium selenium;
    protected final ConditionRunner runner;

    public AbstractSeleniumSteps() {
        this(new SeleniumConfiguration());
    }

    public AbstractSeleniumSteps(Selenium selenium) {
        this(new SeleniumConfiguration(selenium, new SeleniumContext()));
    }

    public AbstractSeleniumSteps(SeleniumConfiguration configuration){
        this.selenium = configuration.getSelenium();
        this.runner = createConditionRunner(selenium);
    }

    /**
     * Creates ConditionRunner used by the Steps, by default
     * {@link com.thoughtworks.selenium.condition.JUnitConditionRunner}.
     *
     * Users may override this method to provide their own custom instance of
     * ConditionRunner.
     *
     * @param selenium the Selenium instance
     * @return A ConditionRunner
     */
    protected ConditionRunner createConditionRunner(Selenium selenium) {
        return new JUnitConditionRunner(selenium, 10, 100, 1000);
    }

    /**
     * Waits for a number of seconds
     *
     * @param seconds the number of seconds to sleep
     */
    protected void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // continue
        }
    }

}
