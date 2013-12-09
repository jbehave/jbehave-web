package org.jbehave.web.selenium;

import org.jbehave.core.steps.StepMonitor;

import com.thoughtworks.selenium.Selenium;

/**
 * Decorator of {@link StepMonitor} which adds communication of current context
 * to {@link ContextView}.
 */
public class SeleniumStepMonitor extends org.jbehave.core.steps.ContextStepMonitor {

    public SeleniumStepMonitor(Selenium selenium, SeleniumContext context, StepMonitor delegate) {
        this(new SeleniumContextView(selenium), context, delegate);
    }

    public SeleniumStepMonitor(ContextView contextView, SeleniumContext context, StepMonitor delegate) {
        super((org.jbehave.core.steps.ContextView) contextView, context, delegate);
    }
    
}