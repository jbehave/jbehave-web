package org.jbehave.web.selenium;

import org.jbehave.core.steps.DelegatingStepMonitor;
import org.jbehave.core.steps.StepMonitor;

import com.thoughtworks.selenium.Selenium;

/**
 * Decorator of {@link StepMonitor} which adds communication of current context
 * to {@link ContextView}.
 */
public class SeleniumStepMonitor extends DelegatingStepMonitor {

    private final ContextView contextView;
    private final SeleniumContext context;

    public SeleniumStepMonitor(Selenium selenium, SeleniumContext context, StepMonitor delegate) {
        this(new SeleniumContextView(selenium), context, delegate);
    }

    public SeleniumStepMonitor(ContextView contextView, SeleniumContext context, StepMonitor delegate) {
        super(delegate);
        this.contextView = contextView;
        this.context = context;
    }

    public void performing(String step, boolean dryRun) {
        contextView.show(context.getCurrentScenario(), step);
        super.performing(step, dryRun);
    }

}