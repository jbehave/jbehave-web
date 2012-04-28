#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package pages

import org.jbehave.web.selenium.WebDriverProvider
import org.jbehave.web.selenium.GroovyGebFluentWebDriverPage

class Site extends GroovyGebFluentWebDriverPage {

  def Site(WebDriverProvider webDriverProvider) {
    super(webDriverProvider)
  }

  def cartSize() {
      return ${symbol_dollar}("${symbol_pound}cart").text().replace("Cart", "").trim()
  }

  def cartEmpty() {
      waitFor {
          cartSize().equals("")
      }
  }
}
