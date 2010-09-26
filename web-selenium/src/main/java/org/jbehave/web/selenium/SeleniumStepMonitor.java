package org.jbehave.web.selenium;

import org.jbehave.core.steps.StepMonitor;

import com.thoughtworks.selenium.Selenium;

public class SeleniumStepMonitor extends DelegatingStepMonitor {

    private final Selenium selenium;
    private final SeleniumContext context;

    public SeleniumStepMonitor(Selenium selenium, SeleniumContext context, StepMonitor delegate) {
        super(delegate);
        this.selenium = selenium;
        this.context = context;
    }

    public void performing(String step, boolean dryRun) {
        selenium.setContext("<b>" + context.getCurrentScenario() + "</b><br/>" + step);
        super.performing(step, dryRun);
    }

}