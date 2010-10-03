package org.jbehave.web.examples.trader.webdriver;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class TraderWebSteps {

    private final PageFactory pageFactory;

    public TraderWebSteps(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    @Given("user is on Home page")
    public void userIsOnHomePage(){        
        pageFactory.home().open();        
    }

    @When("user opens Find Steps page")
    public void userClicksOnFindSteps(){        
        pageFactory.findSteps().open();
    }

    @When("user opens Run Story page")
    public void userClicksOnRunStory(){        
        pageFactory.runStory().open();
    }

    @When("user searches for \"$step\"")
    public void userSearchesForSteps(String step){        
        pageFactory.findSteps().find(step);
    }

    @When("user searches for all steps")
    public void userSearchesAllSteps(){        
        pageFactory.findSteps().find("");
    }

    @When("user views with methods")
    public void userViewsWithMethods(){
        pageFactory.findSteps().viewWithMethods();
    }

    @When("user sorts by pattern")
    public void userSortsByPattern(){
        pageFactory.findSteps().sortByPattern();
    }

    @When("user runs story \"$story\"")
    public void userRunsStory(String story){        
        pageFactory.runStory().run(story);
    }
    
    @Then("Run Story page is shown")
    public void runStoryPageIsShown(){
        pageFactory.runStory().pageIsShown();
    }

    @Then("run is successful")
    public void runIsSuccessful(){        
        pageFactory.runStory().runIsSuccessful();
    }

    @Then("Find Steps page is shown")
    public void findStepsPageIsShown(){
        pageFactory.findSteps().pageIsShown();
    }

    @Then("search returns: \"$stepsOrMethods\"")
    public void stepsFound(List<String> stepsOrMethods){   
        pageFactory.findSteps().found(stepsOrMethods);
    }

    @Then("steps instances include: \"$names\"")
    public void stepsInstancesFound(List<String> names){   
        pageFactory.findSteps().found(names);
    }

}
