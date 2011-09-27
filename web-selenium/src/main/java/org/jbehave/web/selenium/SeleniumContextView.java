package org.jbehave.web.selenium;

import com.thoughtworks.selenium.Selenium;

/**
 * Selenium-based ContextView, that delegates to the
 * {@link Selenium#setContext(String)}.
 */
public class SeleniumContextView implements ContextView {

    private Selenium selenium;

    public SeleniumContextView(Selenium selenium) {
        this.selenium = selenium;
    }

    public void show(String scenario, String step) {
        selenium.setContext(scenario + "<br/>" + step);
    }

    public void close() {
        // do nothing, view is controlled by Selenium Server
    }

}
