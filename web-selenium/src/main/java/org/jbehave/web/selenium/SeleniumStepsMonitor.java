package org.jbehave.web.selenium;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepType;

import com.thoughtworks.selenium.Selenium;

public class SeleniumStepsMonitor implements StepMonitor {
	private final Selenium selenium;
	private final SeleniumContext seleniumContext;
	private final StepMonitor delegate;

	public SeleniumStepsMonitor(Selenium selenium,
			SeleniumContext seleniumContext, StepMonitor delegate) {
		this.selenium = selenium;
		this.seleniumContext = seleniumContext;
		this.delegate = delegate;
	}

	public void performing(String step, boolean dryRun){
		String context = seleniumContext.getCurrentScenario() + "<br>" + step;
		selenium.setContext(context);
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

	public void foundArg(String arg, int position) {
		delegate.foundArg(arg, position);		
	}

	public void usingAnnotatedNameForArg(String name, int position) {
		delegate.usingAnnotatedNameForArg(name, position);
	}

	public void usingNaturalOrderForArg(int position) {
		delegate.usingNaturalOrderForArg(position);
	}

	public void usingParameterNameForArg(String name, int position) {
		delegate.usingParameterNameForArg(name, position);
	}

	public void usingTableAnnotatedNameForArg(String name, int position) {
		delegate.usingTableAnnotatedNameForArg(name, position);
	}

	public void usingTableParameterNameForArg(String name, int position) {
		delegate.usingTableParameterNameForArg(name, position);
	}

}