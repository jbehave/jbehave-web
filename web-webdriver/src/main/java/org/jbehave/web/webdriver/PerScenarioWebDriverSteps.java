package org.jbehave.web.webdriver;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.openqa.selenium.WebDriver;

import static org.jbehave.web.webdriver.WebDriverConfiguration.defaultWebDriver;

/**
 * Steps implementation that can be used in WebDriver-based stories. It provides
 * annotated methods to start and stop WebDriver before and after scenarios.
 * 
 * @author Mauro Talevi
 */
public class PerScenarioWebDriverSteps extends WebDriverSteps {
    public PerScenarioWebDriverSteps(WebDriver driver) {
        super(driver);
    }

    @BeforeScenario
    public void beforeScenario() throws Exception {
        setDriver(defaultWebDriver());
    }

    @AfterScenario
    public void afterScenario() throws Exception {
        getDriver().close();
        setDriver(null);
    }

}
