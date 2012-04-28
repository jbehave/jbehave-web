#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages;

import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;

import static org.openqa.selenium.By.id;
import static org.seleniumhq.selenium.fluent.Period.secs;

public class Site extends FluentWebDriverPage {

    public Site(WebDriverProvider webDriverProvider) {
        super(webDriverProvider);
    }

    public int cartSize() {
        String cartSize = within(secs(2)).div(id("cart")).getText().replace("Cart", "").trim();
        if (cartSize.equals("")) {
            return 0;
        }
        return Integer.parseInt(cartSize);
    }

}
