package org.jbehave.web.examples.trader;

import org.jbehave.core.annotations.Given;
import org.jbehave.web.examples.trader.pages.Home;
import org.jbehave.web.examples.trader.pages.PageFactory;

public class TraderWebSteps {

    private final PageFactory pageFactory;

    public TraderWebSteps(PageFactory pageFactory) {
        this.pageFactory = pageFactory;
    }

    @Given("user is on home page")
    public void userIsOnHomePage(){        
        Home home = pageFactory.home();
        home.open();        
    }

}
