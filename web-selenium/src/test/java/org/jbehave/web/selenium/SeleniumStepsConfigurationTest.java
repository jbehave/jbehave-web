package org.jbehave.web.selenium;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.Selenium;

@RunWith(JMock.class)
public class SeleniumStepsConfigurationTest {

	private Mockery mockery = new Mockery();

	private final Selenium selenium = mockery.mock(Selenium.class);

	@Test
	public void canConfigureSeleniumContextToShowCurrentScenario() throws Throwable{
		SeleniumContext seleniumContext = new SeleniumContext();
		String currentScenario = "current scenario";
		String step = "a step";
		final String context = currentScenario + "<br>" + step;
		mockery.checking(new Expectations(){{
			one(selenium).setContext(context);
		}});
		SeleniumStepsConfiguration configuration = new SeleniumStepsConfiguration(selenium, seleniumContext);
		seleniumContext.setCurrentScenario(currentScenario);
		configuration.getMonitor().performing(step);		
	}

}
