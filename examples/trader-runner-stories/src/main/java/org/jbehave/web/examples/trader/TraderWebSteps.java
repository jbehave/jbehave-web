package org.jbehave.web.examples.trader;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.examples.trader.pages.FindSteps;
import org.jbehave.web.examples.trader.pages.Home;
import org.jbehave.web.examples.trader.pages.PageFactory;

public class TraderWebSteps {

    private final PageFactory pageFactory;
    private Home home;
    private FindSteps findSteps;

    public TraderWebSteps(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    @Given("user is on home page")
    public void userIsOnHomePage(){        
        home = pageFactory.home();
        home.open();        
    }

    @When("user clicks on Find Steps")
    public void userClicksOnFindSteps(){        
        findSteps = home.findSteps(pageFactory);
    }

    @When("user searches for \"$step\"")
    public void userSearchesForSteps(String step){        
        findSteps.find(step);
    }

    @When("user searches for all steps")
    public void userSearchesAllSteps(){        
        findSteps.find("");
    }
    
    @When("user sorts by pattern")
    public void userSortsByPattern(){
        findSteps.sortByPattern();
    }

    @Then("text is shown: \"$text\"")
    public void textIsPresent(String text){   
        findSteps.textIsVisible(text);
    }

    @Then("search returns: \"$steps\"")
    public void stepsFound(List<String> steps){   
        findSteps.found(steps);
    }

    @Then("steps instances include: \"$instanceNames\"")
    public void stepsInstancesInclude(List<String> instanceNames){   
        for (String name : instanceNames) {
            findSteps.found(name);            
        }
    }

}
