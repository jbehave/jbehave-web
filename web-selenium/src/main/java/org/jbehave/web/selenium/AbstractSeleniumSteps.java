package org.jbehave.web.selenium;

import java.util.concurrent.TimeUnit;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.JUnitConditionRunner;

/**
 * Base steps class that can be used in Selenium-based scenarios. It provides
 * instances of {@link Selenium} and {@link ConditionRunner} to subclasses. The
 * Selenium instance is injected (defaulting to {@link #defaultSelenium()}),
 * while the ConditionRunner is can be specified by overriding implementation of
 * {@link #createConditionRunner(Selenium)}.
 */
public class AbstractSeleniumSteps {

    protected final Selenium selenium;
    protected final ConditionRunner runner;

    public AbstractSeleniumSteps() {
        this(SeleniumConfiguration.defaultSelenium());
    }

    public AbstractSeleniumSteps(Selenium selenium) {
        this.selenium = selenium;
        this.runner = createConditionRunner(selenium);
    }

    /**
     * Creates a ConditionRunner, by default {@link
     * JUnitConditionRunner(selenium, 10, 100, 1000)}.
     * 
     * Users may override this method to provide their own custom instance of
     * ConditionRunner.
     * 
     * @param selenium
     *            the Selenium instance
     * @return A ConditionRunner
     */
    protected ConditionRunner createConditionRunner(Selenium selenium) {
        return new JUnitConditionRunner(selenium, 10, 100, 1000);
    }

    /**
     * Waits for a number of seconds
     * 
     * @param seconds
     *            the number of seconds to sleep
     */
    protected void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // continue
        }
    }

}
