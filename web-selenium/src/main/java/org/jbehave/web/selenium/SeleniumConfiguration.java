package org.jbehave.web.selenium;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.StepMonitor;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumConfiguration extends Configuration {

    public static Selenium defaultSelenium() {
        return new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080");
    }

    private final Selenium selenium;

    public SeleniumConfiguration() {
        this(defaultSelenium(), new SeleniumContext(), new SilentStepMonitor());
    }

    public SeleniumConfiguration(Selenium selenium, SeleniumContext seleniumContext, StepMonitor stepMonitor) {
        this.selenium = selenium;
        useStepMonitor(new SeleniumStepsMonitor(selenium, seleniumContext, stepMonitor));
    }

    public Selenium getSelenium() {
        return selenium;
    }

}
