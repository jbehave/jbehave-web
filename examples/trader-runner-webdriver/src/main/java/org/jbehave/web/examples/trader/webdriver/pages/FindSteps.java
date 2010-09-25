package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.webdriver.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.fail;

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
        for (String step : steps) {
            if (!pageSource.contains(step.replace("<", "&lt;").replace(">", "&gt;"))) {
                fail("Step:**" + step + "** not found in page **" + pageSource + "**");
            }
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
