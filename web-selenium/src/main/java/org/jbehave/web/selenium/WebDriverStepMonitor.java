package org.jbehave.web.selenium;

import org.jbehave.core.steps.StepMonitor;

public class WebDriverStepMonitor extends DelegatingStepMonitor {

    private final ContextView contextView;
	private final SeleniumContext context;

    public WebDriverStepMonitor(ContextView contextView, SeleniumContext context, StepMonitor delegate) {
		super(delegate);
        this.contextView = contextView;
		this.context = context;
    }

	public void performing(String step, boolean dryRun){
        contextView.show("<b>" + context.getCurrentScenario() + "</b><br>" + step);
		super.performing(step, dryRun);
	}

}