package org.jbehave.web.examples.trader.selenium.steps;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.examples.trader.selenium.pages.Pages;

public class TraderWebSteps {

    private final Pages pages;

    public TraderWebSteps(Pages pages) {
        this.pages = pages;
    }

    @Given("user is on home page")
    public void userIsOnHomePage(){        
        pages.home().open();
    }

    @When("user clicks on Find Steps")
    public void userClicksOnFindSteps(){        
        pages.findSteps().open();
    }

    @When("user clicks on Run Story")
    public void userClicksOnRunStory(){       
        pages.runStory().open();
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
    public void userViewWithMethods(){
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

    @Then("run is successful")
    public void runIsSuccessful(){        
        pages.runStory().runIsSuccessful();
    }

    @Then("text is shown: \"$text\"")
    public void textIsPresent(String text){   
        pages.findSteps().found(text);
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
