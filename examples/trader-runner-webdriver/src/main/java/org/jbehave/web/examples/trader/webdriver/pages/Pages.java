package org.jbehave.web.examples.trader.webdriver.pages;

import org.jbehave.web.selenium.WebDriverProvider;

public class Pages {

    private final WebDriverProvider driverProvider;
    private Home home;
    private DataFiles dataFiles;
    private FindSteps findSteps;
    private RunStory runStory;

    public Pages(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Home home(){
        if ( home == null ){
            home = new Home(driverProvider);
        }
        return home;
    }
    
    public DataFiles dataFiles() {
        if ( dataFiles == null ){
            dataFiles = new DataFiles(driverProvider);
        }
        return dataFiles;
    }

    public FindSteps findSteps() {
        if ( findSteps == null ){
            findSteps = new FindSteps(driverProvider);
        }
        return findSteps;
    }

    public RunStory runStory() {
        if ( runStory == null ){
            runStory = new RunStory(driverProvider);
        }
        return runStory;
    }

    
}
