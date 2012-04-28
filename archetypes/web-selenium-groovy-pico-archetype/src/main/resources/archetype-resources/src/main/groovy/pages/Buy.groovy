#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package pages

import org.jbehave.web.selenium.WebDriverProvider
import org.jbehave.web.selenium.GroovyGebFluentWebDriverPage

class Buy extends GroovyGebFluentWebDriverPage {

  def Buy(WebDriverProvider webDriverProvider) {
    super(webDriverProvider)
  }

  def selectTreasury() {
    ${symbol_dollar}("${symbol_pound}treasury-panel-button").click()
  }
}
