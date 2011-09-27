package org.jbehave.web.selenium;

import java.lang.reflect.Method;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.model.StepPattern;
import org.jbehave.core.steps.DelegatingStepMonitor;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepType;
import org.junit.Test;

import com.thoughtworks.selenium.Selenium;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SeleniumConfigurationTest {
    
    private Selenium selenium = mock(Selenium.class);
    private StepMonitor stepMonitor = mock(StepMonitor.class);

    @Test
    public void canConfigureSeleniumContextToShowCurrentScenario() throws Throwable {
        SeleniumContext seleniumContext = new SeleniumContext();
        String currentScenario = "current scenario";
        String step = "a step";
        String context = currentScenario + "<br/>" + step;
        boolean dryRun = false;
        Configuration configuration = new SeleniumConfiguration().useSelenium(selenium).useSeleniumContext(
                seleniumContext).useStepMonitor(new SeleniumStepMonitor(selenium, seleniumContext, stepMonitor));
        seleniumContext.setCurrentScenario(currentScenario);
        configuration.stepMonitor().performing(step, dryRun);
        
        verify(selenium).setContext(context);
        verify(stepMonitor).performing(step, dryRun);
    }

    @Test
    public void canDelegateOtherSeleniumStepsMonitorCalls() throws Throwable {
        String step = "a step";
        String value = "value";
        Class<String> type = String.class;
        String converted = "converted";
        Class<String> converterClass = String.class;
        StepPattern pattern = new StepPattern(StepType.GIVEN, "pattern", "p4tt3rn");
        boolean matches = true;
        Method method = null;
        Object stepsInstance = new Object();
        DelegatingStepMonitor monitor = new SeleniumStepMonitor(selenium, new SeleniumContext(), stepMonitor);
        monitor.convertedValueOfType(value, type, converted, converterClass);
        monitor.stepMatchesPattern(step, matches, pattern, method, stepsInstance);
        verify(stepMonitor).convertedValueOfType(value, type, converted, converterClass);
        verify(stepMonitor).stepMatchesPattern(step, matches, pattern, method, stepsInstance);

    }

}
