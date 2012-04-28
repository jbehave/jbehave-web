#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.etsy.pages;

import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;

public class Buy extends FluentWebDriverPage {

    public Buy(WebDriverProvider webDriverProvider) {
        super(webDriverProvider);
    }

    public void selectTreasury() {
        link(By.linkText("Treasury")).click();
    }
}