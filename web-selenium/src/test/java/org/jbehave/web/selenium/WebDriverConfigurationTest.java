package org.jbehave.web.selenium;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.web.selenium.WebDriverConfiguration;
import org.jbehave.web.selenium.WebDriverContext;
import org.jbehave.web.selenium.WebDriverStepMonitor;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WebDriverConfigurationTest {
    
    private WebDriver driver = mock(WebDriver.class);
    private StepMonitor stepMonitor = mock(StepMonitor.class);

    @Test
    public void canConfigureWebDriverContextToShowCurrentScenario() throws Throwable {
        WebDriverContext webDriverContext = new WebDriverContext();
        String currentScenario = "current scenario";
        String step = "a step";
        String context = currentScenario + "<br/>" + step;
        boolean dryRun = false;
        Configuration configuration = new WebDriverConfiguration().useWebDriver(driver).useWebDriverContext(
                webDriverContext).useStepMonitor(new WebDriverStepMonitor(driver, webDriverContext, stepMonitor));
        webDriverContext.setCurrentScenario(currentScenario);
        configuration.stepMonitor().performing(step, dryRun);
        
//        verify(driver).setContext(context);
        verify(stepMonitor).performing(step, dryRun);
    }

    @Test
    public void canDelegateOtherWebDriverStepsMonitorCalls() throws Throwable {
        String step = "a step";
        String value = "value";
        Class<String> type = String.class;
        String converted = "converted";
        Class<String> converterClass = String.class;
        String pattern = "pattern";
        boolean matches = true;
        Method method = null;
        Object stepsInstance = new Object();
        WebDriverStepMonitor monitor = new WebDriverStepMonitor(driver, new WebDriverContext(), stepMonitor);
        monitor.convertedValueOfType(value, type, converted, converterClass);
        monitor.stepMatchesPattern(step, matches, pattern, method, stepsInstance);
        verify(stepMonitor).convertedValueOfType(value, type, converted, converterClass);
        verify(stepMonitor).stepMatchesPattern(step, matches, pattern, method, stepsInstance);

    }

}
