package org.jbehave.web.examples.trader.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Home extends TraderPage {

    public Home(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("http://localhost/flight");        
    }

    public FindSteps findSteps(PageFactory factory){
        driver.findElement(By.linkText("Find Steps")).click();
        return factory.findSteps();
    }
    
    public RunStory runStory(PageFactory factory){
        driver.findElement(By.linkText("Run Story")).click();
        return factory.runStory();
    }
}
