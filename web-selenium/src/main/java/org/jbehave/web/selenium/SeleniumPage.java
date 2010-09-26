package org.jbehave.web.selenium;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.concurrent.TimeUnit;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.Condition;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.Not;
import com.thoughtworks.selenium.condition.Presence;
import com.thoughtworks.selenium.condition.Text;
import com.thoughtworks.selenium.condition.ConditionRunner.Context;

/**
 * Abstract base class for pages that use the Selenium API. It contains common
 * page methods, with a view to implement the <a
 * href="http://code.google.com/p/selenium/wiki/PageObjects">Page Objects</a>
 * pattern.
 */
public abstract class SeleniumPage {

    protected final Selenium selenium;
    protected final ConditionRunner conditionRunner;

    public SeleniumPage(Selenium selenium, ConditionRunner conditionRunner) {
        this.selenium = selenium;
        this.conditionRunner = conditionRunner;
    }

    public void open(String url) {
        selenium.open(url);
    }

    public void click(String locator) {
        selenium.click(locator);
    }

    public void clickButton(String name) {
        click("//input[@value='" + name + "']");
    }

    public void clickLink(String name) {
        click("link=" + name + "");
    }

    public void select(String locator, String option) {
        selenium.select(locator, option);        
    }

    public void selectByLabel(String locator, String label) {
        select(locator, "label="+label);        
    }

    public void selectByValue(String locator, String value) {
        select(locator, "value="+value);        
    }

    public void type(String locator, String value) {
        selenium.type(locator, value);
    }

    public String text(String locator) {
        return selenium.getText(locator);
    }

    public String value(String locator) {
        return selenium.getValue(locator);
    }

    public void textIsVisible(String text) {
        textIsVisible(text, null);
    }

    public void textIsVisible(String text, String locator) {
        waitFor(new Text(text, locator));
    }

    public void textIsNotVisible(String text) {
        textIsNotVisible(text, null);
    }

    public void textIsNotVisible(String text, String locator) {
        waitFor(new Not(new Text(text, locator)));
    }

    public void waitFor(Condition condition) {
        conditionRunner.waitFor(condition);
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(30);
    }

    public void waitForPageToLoad(int seconds) {
        String timeout = String.valueOf(seconds * 1000);
        selenium.waitForPageToLoad(timeout);
    }

    public static void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // continue
        }
    }

    public static class NonBlank extends Presence {
        private String locator;

        public NonBlank(String locator) {
            super(locator);
            this.locator = locator;
        }

        @Override
        public boolean isTrue(Context context) {
            String text = null;
            if (super.isTrue(context)) {
                text = context.getSelenium().getText(locator);
            }
            return isNotBlank(text);
        }
    }

}
