package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class FindSteps extends AbstractPage {

    public FindSteps(WebDriverProvider driverProvider) {
        super(driverProvider);
    }
    
    public void open(){
        findElement(By.linkText("Find Steps")).click();
    }

    public void pageIsShown() {
        found("Match");
    }

    public void find(String step) {
        findElement(By.name("matchingStep")).sendKeys(step);
        findElement(By.name("findButton")).click();
    }

    public void viewWithMethods() {
        new Select(findElement(By.name("viewSelect"))).selectByVisibleText("WITH_METHODS");
    }

    public void sortByPattern() {
        new Select(findElement(By.name("sortingSelect"))).selectByVisibleText("BY_PATTERN");
    }

}
