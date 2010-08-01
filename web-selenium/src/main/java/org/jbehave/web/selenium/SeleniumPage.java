package org.jbehave.web.selenium;

import java.util.concurrent.TimeUnit;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.condition.Condition;
import com.thoughtworks.selenium.condition.ConditionRunner;
import com.thoughtworks.selenium.condition.Not;
import com.thoughtworks.selenium.condition.Presence;
import com.thoughtworks.selenium.condition.Text;
import com.thoughtworks.selenium.condition.ConditionRunner.Context;

/**
 * Abstract base class for all Selenium-based pages. It contains methods common
 * to all pages, with a view to implement the <a
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

    public void textIsVisible(String text) {
        waitFor(new Text(text));
    }

    public void textIsNotVisible(String text) {
        waitFor(new Not(new Text(text)));
    }

    public void waitFor(Condition condition) {
        conditionRunner.waitFor(condition);
        waitFor(1);
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
            String text = "";
            if (super.isTrue(context)) {
                text = context.getSelenium().getText(locator);
            }
            return !text.equals("");
        }
    }

}
