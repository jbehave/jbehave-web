package org.jbehave.web.selenium;

import java.lang.reflect.Method;

import org.jbehave.core.steps.StepMonitor;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.Selenium;

@RunWith(JMock.class)
public class SeleniumStepsConfigurationTest {

	private Mockery mockery = new Mockery();

	private Selenium selenium = mockery.mock(Selenium.class);
	private StepMonitor stepMonitor = mockery.mock(StepMonitor.class);

	@Test
	public void canConfigureSeleniumContextToShowCurrentScenario() throws Throwable{
		SeleniumContext seleniumContext = new SeleniumContext();
		String currentScenario = "current scenario";
		final String step = "a step";
		final String context = currentScenario + "<br>" + step;
		final boolean dryRun = false;
		mockery.checking(new Expectations(){{
			one(selenium).setContext(context);
			one(stepMonitor).performing(step, dryRun);
		}});
		SeleniumStepsConfiguration configuration = new SeleniumStepsConfiguration(selenium, seleniumContext, stepMonitor);
		seleniumContext.setCurrentScenario(currentScenario);
		configuration.monitor().performing(step, dryRun);		
	}

	@Test
	public void canDelegateOtherSeleniumStepsMonitorCalls() throws Throwable{
		final String step = "a step";
		final String value = "value";
		final Class<String> type = String.class;
		final String converted = "converted";
		final Class<String> converterClass = String.class;
		final String pattern = "pattern";
		final boolean matches = true;
		final Method method = null;
		final Object stepsInstance = new Object();
		mockery.checking(new Expectations(){{
			one(stepMonitor).convertedValueOfType(value, type, converted, converterClass);
			one(stepMonitor).stepMatchesPattern(step, matches, pattern, method, stepsInstance);
		}});
		SeleniumStepsMonitor monitor = new SeleniumStepsMonitor(selenium, new SeleniumContext(), stepMonitor);
		monitor.convertedValueOfType(value, type, converted, converterClass);
		monitor.stepMatchesPattern(step, matches, pattern, method, stepsInstance);
	}

}
