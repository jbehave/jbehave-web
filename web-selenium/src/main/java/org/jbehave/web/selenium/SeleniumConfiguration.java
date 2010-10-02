package org.jbehave.web.selenium;

import org.jbehave.core.configuration.Configuration;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.JUnitConditionRunner;

/**
 * Extends Configuration to provide Selenium-based components. 
 * It can be used for both Selenium and WebDriver APIs.
 */
public class SeleniumConfiguration extends Configuration {

    private Selenium selenium = defaultSelenium();
    private SeleniumContext seleniumContext = new SeleniumContext();
    private WebDriverProvider driverProvider;

    public SeleniumConfiguration() {
    }

    public Selenium selenium() {
        return selenium;
    }

    public SeleniumConfiguration useSelenium(Selenium selenium){
        this.selenium = selenium;
        return this;
    }
    
    public SeleniumContext seleniumContext() {
        return seleniumContext;
    }

    public SeleniumConfiguration useSeleniumContext(SeleniumContext seleniumContext) {
        this.seleniumContext = seleniumContext;
        return this;
    }
    
    public WebDriverProvider webDriverProvider() {
        return driverProvider;
    }

    public SeleniumConfiguration useWebDriverProvider(WebDriverProvider webDriverProvider){
        this.driverProvider = webDriverProvider;
        return this;
    }
    
    /**
     * Creates default Selenium instance: {@link DefaultSelenium("localhost",
     * 4444, "*firefox", "http://localhost:8080")}
     * 
     * @return A Selenium instance
     */
    public static Selenium defaultSelenium() {
        return new DefaultSelenium("localhost", 4444, "*firefox", "http://localhost:8080");
    }

    /**
     * Creates default ConditionRunner: {@link JUnitConditionRunner(selenium,
     * 10, 100, 1000)}.
     * 
     * @param selenium
     *            the Selenium instance
     * @return A ConditionRunner
     */
    public static ConditionRunner defaultConditionRunner(Selenium selenium) {
        return new JUnitConditionRunner(selenium, 10, 100, 1000);
    }
    

}
