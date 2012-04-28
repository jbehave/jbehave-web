#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package pages

import java.util.concurrent.TimeUnit
import org.jbehave.web.selenium.WebDriverProvider
import org.openqa.selenium.By
import org.jbehave.web.selenium.GroovyGebFluentWebDriverPage

class Home extends GroovyGebFluentWebDriverPage {

  def Home(WebDriverProvider webDriverProvider) {
    super(webDriverProvider)
  }


  def go() {
    get("http://www.etsy.com")
  }

  def go(String section) {
    go()
    findElement(By.xpath("//a[@title = '" + section + "']")).click()
  }

  def search(String thing) {
    manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS)
    ${symbol_dollar}("${symbol_pound}search-facet").click()
    ${symbol_dollar}(".all").click()
    ${symbol_dollar}("${symbol_pound}search-query") << thing
    ${symbol_dollar}("${symbol_pound}search_submit").click()
  }

  def goToBuySection() {
    findElement(By.linkText("Buy")).click()
  }
}
