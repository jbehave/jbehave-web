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
        String absolutePath = new File(path).getAbsolutePath();
        findElement(By.className("wicket-mfu-field")).sendKeys(absolutePath);
        findElement(By.name("uploadButton")).click();
    }
    
}
