package org.jbehave.web.examples.trader.webdriver;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.examples.trader.webdriver.pages.FindSteps;
import org.jbehave.web.examples.trader.webdriver.pages.Home;
import org.jbehave.web.examples.trader.webdriver.pages.RunStory;

public class TraderWebSteps {

    private Home home;
    private FindSteps findSteps;
    private RunStory runStory;

    public TraderWebSteps(PageFactory pageFactory) {
        home = pageFactory.home();
        findSteps = pageFactory.findSteps();
        runStory = pageFactory.runStory();
    }

    @Given("user is on home page")
    public void userIsOnHomePage(){        
        home.open();        
    }

    @When("user clicks on Find Steps")
    public void userClicksOnFindSteps(){        
        home.openFindSteps();
    }

    @When("user clicks on Run Story")
    public void userClicksOnRunStory(){        
        home.openRunStory();
    }

    @When("user searches for \"$step\"")
    public void userSearchesForSteps(String step){        
        findSteps.find(step);
    }

    @When("user searches for all steps")
    public void userSearchesAllSteps(){        
        findSteps.find("");
    }

    @When("user views with methods")
    public void userViewsWithMethods(){
        findSteps.viewWithMethods();
    }

    @When("user sorts by pattern")
    public void userSortsByPattern(){
        findSteps.sortByPattern();
    }

    @When("user runs story \"$story\"")
    public void userRunsStory(String story){        
        runStory.run(story);
    }
    
    @Then("Run Story page is shown")
    public void runStoryPageIsShown(){
        runStory.pageIsShown();
    }

    @Then("run is successful")
    public void runIsSuccessful(){        
        runStory.runIsSuccessful();
    }

    @Then("Find Steps page is shown")
    public void findStepsPageIsShown(){
        findSteps.pageIsShown();
    }

    @Then("search returns: \"$stepsOrMethods\"")
    public void stepsFound(List<String> stepsOrMethods){   
        findSteps.found(stepsOrMethods);
    }

    @Then("steps instances include: \"$names\"")
    public void stepsInstancesFound(List<String> names){   
        findSteps.found(names);
    }

}
