#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package pages

import org.jbehave.web.selenium.WebDriverProvider
import org.jbehave.web.selenium.GroovyGebFluentWebDriverPage
import org.openqa.selenium.By;

class Buy extends GroovyGebFluentWebDriverPage {

  def Buy(WebDriverProvider webDriverProvider) {
    super(webDriverProvider)
  }

  def selectTreasury() {
      link(By.partialLinkText("Treasury")).click()
  }
  
}
