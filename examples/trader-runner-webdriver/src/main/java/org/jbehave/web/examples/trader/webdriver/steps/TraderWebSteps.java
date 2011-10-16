package org.jbehave.web.examples.trader.webdriver.steps;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.examples.trader.webdriver.pages.Pages;

public class TraderWebSteps {

    private final Pages pages;

    public TraderWebSteps(Pages pages) {
        this.pages = pages;
    }

    @Given("user is on Home page")
    public void userIsOnHomePage(){        
        pages.home().open();        
    }

    @When("user opens Data Files page")
    public void userClicksOnDataFiles(){        
        pages.dataFiles().open();
    }

    @Then("Data Files page is shown")
    public void dataFilesPageIsShown(){
        pages.dataFiles().pageIsShown();
    }

    @When("user uploads file $path")
    public void userUploadsFile(String path){        
        pages.dataFiles().uploadFile(path);
    }    

    @Then("file $name is uploaded")
    public void fileIsUploaded(String name){        
        pages.dataFiles().found(name);
    }        
    
    @When("user opens Find Steps page")
    public void userClicksOnFindSteps(){        
        pages.findSteps().open();
    }

    @Then("Find Steps page is shown")
    public void findStepsPageIsShown(){
        pages.findSteps().pageIsShown();
    }

    @When("user opens Run Story page")
    public void userClicksOnRunStory(){        
        pages.runStory().open();
    }
    
    @Then("Run Story page is shown")
    public void runStoryPageIsShown(){
        pages.runStory().pageIsShown();
    }
    
    @When("user searches for \"$step\"")
    public void userSearchesForSteps(String step){        
        pages.findSteps().find(step);
    }

    @When("user searches for all steps")
    public void userSearchesAllSteps(){        
        pages.findSteps().find("");
    }

    @When("user views with methods")
    public void userViewsWithMethods(){
        pages.findSteps().viewWithMethods();
    }

    @When("user sorts by pattern")
    public void userSortsByPattern(){
        pages.findSteps().sortByPattern();
    }

    @When("user runs story \"$story\"")
    public void userRunsStory(String story){        
        pages.runStory().run(story);
    }
    
    @Then("run is successful within timeout of $timeoutInSecs secs")
    public void runIsSuccessful(int timeoutInSecs){        
        pages.runStory().runIsSuccessful(timeoutInSecs);
    }

    @Then("search returns: \"$stepsOrMethods\"")
    public void stepsFound(List<String> stepsOrMethods){   
        pages.findSteps().found(stepsOrMethods);
    }

    @Then("steps instances include: \"$names\"")
    public void stepsInstancesFound(List<String> names){   
        pages.findSteps().found(names);
    }

}
