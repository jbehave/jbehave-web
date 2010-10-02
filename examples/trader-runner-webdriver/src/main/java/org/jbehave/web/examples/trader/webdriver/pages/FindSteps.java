package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.Assert.fail;

public class FindSteps extends TraderPage {

    public FindSteps(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void find(String step) {
        findElement(By.name("matchingStep")).sendKeys(step);
        findElement(By.name("findButton")).click();
    }

    public void found(String step) {
        found(getPageSource(), step);
    }

    public void found(List<String> steps) {
        String pageSource = getPageSource();
        for (String step : steps) {
            found(pageSource, step);
        }
    }

    private void found(String pageSource, String step) {
        if (!pageSource.contains(escapeToHtml(step))) {
            fail("Step:**" + step + "** not found in page **" + pageSource + "**");
        }
    }

    private String escapeToHtml(String step) {
        return step.replace("<", "&lt;").replace(">", "&gt;");
    }

    public void viewWithMethods() {
        new Select(findElement(By.name("viewSelect"))).selectByVisibleText("WITH_METHODS");
    }

    public void sortByPattern() {
        new Select(findElement(By.name("sortingSelect"))).selectByVisibleText("BY_PATTERN");
    }

}
