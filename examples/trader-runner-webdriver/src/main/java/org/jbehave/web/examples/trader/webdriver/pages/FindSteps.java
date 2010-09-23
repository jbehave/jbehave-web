package org.jbehave.web.examples.trader.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class FindSteps extends TraderPage {

    public FindSteps(WebDriver driver) {
        super(driver);
    }

    public void find(String step) {
        findElement(By.name("matchingStep")).sendKeys(step);
        findElement(By.name("findButton")).click();
    }

    public void found(String step) {
        getPageSource().contains(step);
    }

    public void found(List<String> steps) {
        String pageSource = getPageSource();
        for ( String step : steps ){
            pageSource.contains(step);
        }
    }

    public void viewWithMethods() {
        Select dropDown = new Select(findElement(By.name("viewSelect")));
        dropDown.selectByVisibleText("WITH_METHODS");
    }

    public void sortByPattern() {
        Select dropDown = new Select(findElement(By.name("sortingSelect")));
        dropDown.selectByVisibleText("BY_PATTERN");
    }

}
