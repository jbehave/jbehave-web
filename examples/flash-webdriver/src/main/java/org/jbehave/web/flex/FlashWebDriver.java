package org.jbehave.web.flex;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FlashWebDriver {
    
    private WebDriver driver;
    private String flashObjectId;

    public FlashWebDriver(WebDriver driver, String flashObjectId, String url) {
        this.driver = driver;
        this.flashObjectId = flashObjectId;
        driver.get(url);
    }

    public Object call(String functionName, String... args) {
        if (driver instanceof JavascriptExecutor) {
            WebElement flashObject = driver.findElement(By.id(flashObjectId));
            String script = jsForFunction(functionName, args);
            Object object = ((JavascriptExecutor) driver).executeScript(script, flashObject);
            return object;
        }
        throw new RuntimeException("WebDriver not a JavascriptExecutor "+driver);
    }

    private String jsForFunction(String functionName, String... args) {
        String functionArgs = "";
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                functionArgs = functionArgs + "'" + args[i] + "',";
            }
            // remove last comma
            functionArgs = functionArgs.substring(0, functionArgs.length() - 1);
        }
        return "return arguments[0]." + functionName + "(" + functionArgs + ");";
    }

    public Object getVariable(String varName) {
        return call("GetVariable", varName);
    }

    public void gotoFrame(int frameNumber) {
        call("GotoFrame", Integer.toString(frameNumber));
    }

    public void pan(int x, int y, int mode) {
        call("Pan", Integer.toString(x), Integer.toString(y), Integer.toString(mode));
    }

    public int percentLoaded() {
        return new Integer(call("PercentLoaded").toString()).intValue();
    }

}
