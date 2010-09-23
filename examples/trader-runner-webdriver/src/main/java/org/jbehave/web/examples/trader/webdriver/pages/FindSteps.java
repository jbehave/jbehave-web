package org.jbehave.web.examples.trader.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FindSteps extends TraderPage {

    public FindSteps(WebDriver driver) {
        super(driver);
    }

    public void find(String step) {
        findElement(By.id("matchingStep")).sendKeys(step);
        findElement(By.linkText("Find")).click();
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
        WebElement element = findElement(By.id("viewSelect"));
        element.setSelected(); // "WITH_METHODS"
        // ?
    }

    public void sortByPattern() {
        WebElement element = findElement(By.id("sortingSelect"));
        element.setSelected(); // "BY_PATTERN"
    }

}
