package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.webdriver.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class FindSteps extends TraderPage {

    public FindSteps(WebDriverFactory driver) {
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
            assertTrue(pageSource.contains(step));
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
