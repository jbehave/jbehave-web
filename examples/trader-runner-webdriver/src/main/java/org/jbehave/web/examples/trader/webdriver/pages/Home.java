package org.jbehave.web.examples.trader.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class Home extends TraderPage {

    public Home(WebDriver driver) {
        super(driver);
    }

    public void open() {
        get("http://localhost:8080/trader-runner");
        manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public FindSteps findSteps(PageFactory factory){
        findElement(By.linkText("Find Steps")).click();
        return factory.findSteps();
    }
    
    public RunStory runStory(PageFactory factory){
        findElement(By.linkText("Run Story")).click();
        return factory.runStory();
    }
}
