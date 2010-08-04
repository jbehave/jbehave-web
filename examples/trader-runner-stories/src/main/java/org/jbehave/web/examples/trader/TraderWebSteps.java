package org.jbehave.web.examples.trader;

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

    @Then("text is shown: \"$text\"")
    public void textIsPresent(String text){   
        findSteps.textIsVisible(text);
    }

    @Then("search returns: \"$result\"")
    public void searchReturns(String result){   
        findSteps.found(result);
    }

}
