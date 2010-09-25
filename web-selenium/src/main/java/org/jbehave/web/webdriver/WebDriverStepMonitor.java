package org.jbehave.web.webdriver;

import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepType;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class WebDriverStepMonitor implements StepMonitor {

	private final WebDriverContext webDriverContext;
	private final StepMonitor delegate;
    private final Notifier notifier;
    private WebDriverFactory driverFactory;

    public WebDriverStepMonitor(WebDriverFactory driverFactory,
			WebDriverContext webDriverContext, StepMonitor delegate, Notifier notifier) {
		this.driverFactory = driverFactory;
		this.webDriverContext = webDriverContext;
		this.delegate = delegate;
        this.notifier = notifier;
    }

	public void performing(String step, boolean dryRun){
        notifier.notify("<b>" + webDriverContext.getCurrentScenario() + "</b><br>" + step);
		delegate.performing(step, dryRun);
	}

	public void convertedValueOfType(String value, Type type, Object converted,
			Class<?> converterClass) {
		delegate.convertedValueOfType(value, type, converted, converterClass);
	}

	public void stepMatchesType(String stepAsString, String previousAsString,
			boolean matchesType, StepType stepType, Method method, Object stepsInstance){
		delegate.stepMatchesType(stepAsString, previousAsString, matchesType, stepType, method, stepsInstance);		
	}

	public void stepMatchesPattern(String step, boolean matches, String pattern, Method method, Object stepsInstance){
		delegate.stepMatchesPattern(step, matches, pattern, method, stepsInstance);		
	}

	public void foundParameter(String parameter, int position) {
		delegate.foundParameter(parameter, position);		
	}

	public void usingAnnotatedNameForParameter(String name, int position) {
		delegate.usingAnnotatedNameForParameter(name, position);
	}

	public void usingNaturalOrderForParameter(int position) {
		delegate.usingNaturalOrderForParameter(position);
	}

	public void usingParameterNameForParameter(String name, int position) {
		delegate.usingParameterNameForParameter(name, position);
	}

	public void usingTableAnnotatedNameForParameter(String name, int position) {
		delegate.usingTableAnnotatedNameForParameter(name, position);
	}

	public void usingTableParameterNameForParameter(String name, int position) {
		delegate.usingTableParameterNameForParameter(name, position);
	}

}