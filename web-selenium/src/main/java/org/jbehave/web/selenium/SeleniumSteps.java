package org.jbehave.web.selenium;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.JUnitConditionRunner;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Steps implementation that can be used in Selenium-based scenarios. It
 * provides annotated methods to start and stop Selenium before and after
 * scenarios. It also provides defaults for Selenium and ConditionRunner
 * dependencies, which may be overridden by user when providing the
 * implementation of scenario steps.
 * 
 * @author Mauro Talevi
 */
public abstract class SeleniumSteps extends Steps {

	protected final Selenium selenium;
	protected final ConditionRunner runner;

	public SeleniumSteps() {
		this(new StepsConfiguration());
	}

	public SeleniumSteps(StepsConfiguration configuration){
		super(configuration);
		this.selenium = createSelenium();
		this.runner = createConditionRunner(selenium);
	}

    public SeleniumSteps(Selenium selenium) {
        this(new SeleniumStepsConfiguration(selenium, new SeleniumContext()));
    }

	public SeleniumSteps(SeleniumStepsConfiguration configuration){
		super(configuration);
		this.selenium = configuration.getSelenium();
		this.runner = createConditionRunner(selenium);
	}

	/**
	 * Creates Selenium used by the Steps, by default {@link DefaultSelenium}
	 * using "*firefox" as browser on localhost.
	 * 
	 * Users may override this method to provide their own custom instance of
	 * Selenium.
	 * 
	 * @return A Selenium instance
     * @deprecated Use second constructor instead
	 */
	protected Selenium createSelenium() {
		return new DefaultSelenium("localhost", 4444, "*firefox",
				"http://localhost:8080");
	}

	/**
	 * Creates ConditionRunner used by the Steps, by default
	 * {@link JUnitConditionRunner}.
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
