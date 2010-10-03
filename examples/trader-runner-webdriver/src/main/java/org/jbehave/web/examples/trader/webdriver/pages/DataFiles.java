package org.jbehave.web.examples.trader.webdriver.pages;

import java.io.File;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

public class DataFiles extends AbstractPage {

    public DataFiles(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open() {
        findElement(By.linkText("Data Files")).click();
    }

    public void pageIsShown() {
        found("Data Files");
    }

    public void uploadFile(String path){
        findElement(By.className("wicket-mfu-field")).sendKeys(new File("src/main/resources"+path).getAbsolutePath());
        findElement(By.name("uploadButton")).submit();
        switchTo().defaultContent();
    }
    
}
