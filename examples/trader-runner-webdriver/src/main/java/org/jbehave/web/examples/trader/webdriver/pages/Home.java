package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.examples.trader.webdriver.PageFactory;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

public class Home extends AbstractPage {

    public Home(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open() {
        get("http://localhost:8080/trader-runner/");
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
