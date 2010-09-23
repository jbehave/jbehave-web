package org.jbehave.web.examples.trader.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static junit.framework.Assert.assertFalse;

public class RunStory extends TraderPage {

    public RunStory(WebDriver driver) {
        super(driver);
    }

    public void run(String story) {
        findElement(By.name("input")).sendKeys(story);
        findElement(By.linkText("Run")).click();
    }

    public void runIsSuccessful() {
        String page = getPageSource();
        assertTrue(page.contains("Scenario"));
        assertFalse(page.contains("FAILED"));
        assertFalse(page.contains("PENDING"));
    }

}
