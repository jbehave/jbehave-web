package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;

/**
 * Base steps class that can be used in scenarios that use the Selenium API. It
 * provides instances of {@link Selenium} and {@link ConditionRunner} to
 * subclasses. The instances are injected, defaulting to
 * {@link SeleniumConfiguration#defaultSelenium()} and
 * {@link SeleniumConfiguration#defaultConditionRunner(Selenium)}.
 */
public class SeleniumSteps {

    protected final Selenium selenium;
    protected final ConditionRunner runner;

    public SeleniumSteps() {
        this(SeleniumConfiguration.defaultSelenium());
    }

    public SeleniumSteps(Selenium selenium) {
        this(selenium, SeleniumConfiguration.defaultConditionRunner(selenium));
    }

    public SeleniumSteps(Selenium selenium, ConditionRunner runner) {
        this.selenium = selenium;
        this.runner = runner;
    }

}
