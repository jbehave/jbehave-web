package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.selenium.WebDriverFactory;
import org.openqa.selenium.By;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static junit.framework.Assert.assertFalse;

public class RunStory extends TraderPage {

    public RunStory(WebDriverFactory driverFactory) {
        super(driverFactory);
    }

    public void run(String story) {
        findElement(By.name("input")).sendKeys(story);
        findElement(By.name("runButton")).click();
    }

    public void runIsSuccessful() {
        String page = getPageSource();
        assertTrue(page.contains("Scenario"));
        assertFalse(page.contains("FAILED"));
        assertFalse(page.contains("PENDING"));
    }

}
