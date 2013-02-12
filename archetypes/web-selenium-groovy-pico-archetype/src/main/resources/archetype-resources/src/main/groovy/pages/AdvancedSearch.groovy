#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package pages

import org.jbehave.web.selenium.WebDriverProvider
import org.jbehave.web.selenium.GroovyGebFluentWebDriverPage
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select

class AdvancedSearch extends GroovyGebFluentWebDriverPage {

  def AdvancedSearch(WebDriverProvider webDriverProvider) {
    super(webDriverProvider)
  }

  def go() {
    get("http://www.etsy.com/search_advanced.php")
  }

  def go(String section) {
    go()
    ${symbol_dollar}("a", title : section).click()
  }

  def search(String thing) {
    ${symbol_dollar}("${symbol_pound}search-query").sendKeys(thing)
    ${symbol_dollar}("${symbol_pound}search_submit").click()
  }

  def subCategory(String subCategory) {
    link(By.partialLinkText(subCategory)).click();
  }

  def searchFor(String thing) {
    def field = ${symbol_dollar}("${symbol_pound}search_query")
    field << thing
    field.submit()
  }
}
