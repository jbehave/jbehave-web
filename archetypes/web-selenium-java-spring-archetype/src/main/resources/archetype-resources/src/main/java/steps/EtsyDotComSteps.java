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
import ${package}.pages.AdvancedSearch;
import ${package}.pages.Buy;
import ${package}.pages.CartContents;
import ${package}.pages.Home;
import ${package}.pages.PageFactory;
import ${package}.pages.SearchResults;
import ${package}.pages.Site;
import ${package}.pages.Treasury;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class EtsyDotComSteps {
    
    private AdvancedSearch advancedSearch;
    private Home home;
    private Site site;
    private SearchResults searchResults;
    private CartContents cartContents;
    private Buy buy;
    private Treasury treasury;

    private String justBought = "";

    public EtsyDotComSteps(PageFactory pageFactory){
        advancedSearch = pageFactory.newAdvancedSearch();
        home = pageFactory.newHome();
        site = pageFactory.newSite();
        searchResults = pageFactory.newSearchResults();
        cartContents = pageFactory.newCartContents();
        buy = pageFactory.newBuy();
        treasury = pageFactory.newTreasury();
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

    @Given("I am searching on Etsy.com")
    public void advancedSearchingOnEtsyDotCom() {
        advancedSearch.go();
    }

    @Given("that the cart is empty")
    public void cartIsEmptyAndOnStartPage() {
        home.go();
        cartIsEmpty();
    }

    @Then("the cart will be empty")
    public void cartIsEmpty() {
        assertThat(site.cartSize(), equalTo(0));
    }

    @Then("the cart contents is empty")
    public void cartContentsIsEmpty() {
        assertThat(cartContents.cartSize(), equalTo(0));
    }

    @Given("the cart contains one item")
    public void anItemInTheEtsyCart() {
        shoppingForSomethingOnEtsyDotCom("hat", "Vintage");
        cartIsEmpty();
        putThingInCart("hat");
        cartNotEmpty(1);
    }

    @When("an item is added to the cart")
    public void putThingInCart() {
        putThingInCart("hat");
    }

    @When("I search for an item")
    public void searchForItem() {
        home.search("hat");
    }

    @When("I want to browse through a treasury gallery")
    @Composite(steps = { "When I want to buy something from etsy.com", "When I want to browse the treasury",
            "When I choose the first treasury gallery" })
    public void browseToFirstTreasuryGallery() {
    }

    @When("I want to buy something from etsy.com")
    public void selectBuyTabAtTop() {
        home.goToBuySection();
    }

    @When("I want to browse the treasury")
    public void browseTreasury() {
        buy.selectTreasury();
    }

    @When("I choose the first treasury gallery")
    public void selectFirstTreasuryGallery() {
        treasury.chooseFirstGallery();
    }

    @When("a ${symbol_dollar}thing is placed in the cart")
    public void putThingInCart(String thing) {
        justBought = searchResults.buyFirst(thing);
        assertThat(justBought, Matchers.not("<not-bought>"));
    }

    @When("the item is removed")
    public void removeItem() {
        cartContents.removeItem();
    }

    @When("I specify the ${symbol_dollar}subCat sub category")
    public void specifiSubCategory(String subCat) {
        advancedSearch.subCategory(subCat);
    }

    @When("I search for ${symbol_dollar}thing")
    public void seachForThing(String thing) {
        advancedSearch.searchFor(thing);
    }

    @Then("the cart contains that item")
    public void cartHasThatItem() {
        assertThat(cartContents.hasItem(justBought), Matchers.is(true));
    }

    @Then("the cart has ${symbol_dollar}num items")
    @Alias("the cart has ${symbol_dollar}num item")
    public void cartNotEmpty(int num) {
        assertThat(site.cartSize(), Matchers.equalTo(num));
    }

    @Then("there are search results")
    @Alias("results will be displayed in the gallery")
    public void thereAreSearchResults() {
        assertThat(searchResults.resultsFound(), Matchers.greaterThan(0));
    }
}
