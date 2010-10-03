package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

public class RunStory extends AbstractPage {

    public RunStory(WebDriverProvider driverProvider) {
        super(driverProvider);
    }
    
    public void pageIsShown() {
        found("Story Input");
    }
    
    public void run(String story) {
        findElement(By.name("input")).sendKeys(story);
        findElement(By.name("runButton")).click();
    }

    public void runIsSuccessful() {
        found("Scenario");
        notFound("FAILED");
        notFound("PENDING");
    }

}
