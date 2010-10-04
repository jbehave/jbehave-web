package org.jbehave.web.examples.trader.selenium.steps;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.examples.trader.selenium.pages.PageFactory;

public class TraderWebSteps {

    private final PageFactory pageFactory;

    public TraderWebSteps(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    @Given("user is on home page")
    public void userIsOnHomePage(){        
        pageFactory.home().open();
    }

    @When("user clicks on Find Steps")
    public void userClicksOnFindSteps(){        
        pageFactory.findSteps().open();
    }

    @When("user clicks on Run Story")
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
    public void userViewWithMethods(){
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

    @Then("run is successful")
    public void runIsSuccessful(){        
        pageFactory.runStory().runIsSuccessful();
    }

    @Then("text is shown: \"$text\"")
    public void textIsPresent(String text){   
        pageFactory.findSteps().found(text);
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
