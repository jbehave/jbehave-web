#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
import com.github.tanob.groobe.GrooBe
import org.jbehave.core.annotations.*
import pages.*

public class EtsyDotComSteps {

  Home home

  private String justBought = ""

  def EtsyDotComSteps() {
    GrooBe.activate()
  }

  @Given("I am shopping for a ${symbol_escape}${symbol_dollar}thing in ${symbol_escape}${symbol_dollar}section on Etsy.com")
  def shoppingForSomethingOnEtsyDotCom(String thing, String section) {
    home.go(section)
    home.search(thing)
  }

  @Given("I am on etsy.com")
  def homepageOnEtsyDotCom() {
    home.go()
  }
  
  @When("an item is added to the cart")
  def putThingInCart() {
    putThingInCart("hat")
  }

  @When("I search for an item")
  def searchForItem(){
    home.search("hat")
  }

  @When("I want to buy something from etsy.com")
  def selectBuyTabAtTop(){
    home.goToBuySection()
  }

}
