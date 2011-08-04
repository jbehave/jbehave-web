package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RunStory extends AbstractPage {

    public RunStory(WebDriverProvider driverProvider) {
        super(driverProvider);
    }
    
    public void open(){
        findElement(By.linkText("Run Story")).click();
    }
    
    public void pageIsShown() {
        found("Story Input");
    }
    
    public void run(String story) {
        findElement(By.name("input")).sendKeys(story);
        findElement(By.name("runButton")).click();
        System.out.println();
    }

    public void runIsSuccessful() {

        String output = findElement(By.id("storyOutput")).getText();

        assertTrue("Scenario should have been in story output", output.contains("Scenario"));
        assertFalse("Nothing should have failed", output.contains("FAILED"));
        assertFalse("Nothing should be pending", output.contains("PENDING"));

    }

}
