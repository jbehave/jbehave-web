#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.steps;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Composite;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import ${package}.pages.Home;
import ${package}.pages.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class EtsyDotComSteps {
    
    private Home home;

    public EtsyDotComSteps(PageFactory pageFactory){
        home = pageFactory.newHome();
    }
    
    @Given("I am shopping for a ${symbol_dollar}thing in ${symbol_dollar}section on Etsy.com")
    public void shoppingForSomethingOnEtsyDotCom(String thing, String section) {
        home.go(section);
        home.search(thing);
    }

    @Given("I am on etsy.com")
    public void homepageOnEtsyDotCom() {
        home.go();
    }

    @When("I search for an item")
    public void searchForItem() {
        home.search("hat");
    }

    @When("I want to buy something from etsy.com")
    public void selectBuyTabAtTop() {
        home.goToBuySection();
    }

}
