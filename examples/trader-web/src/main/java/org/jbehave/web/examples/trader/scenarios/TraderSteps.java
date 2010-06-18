package org.jbehave.web.examples.trader.scenarios;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

public class TraderSteps extends Steps {

    private Stock stock;
    private Trader trader;
    private TradingService service;

    public TraderSteps(TradingService service) {
        this.service = service;
    }

    @Given("a threshold of $threshold")
    @Alias("a limit of $threshold")
    public void aThreshold(double threshold) {
        stock = service.newStock(threshold);
    }

    @When("the stock is traded at $price")
    public void theStockIsTradedAt(double price) {
        stock.tradeAt(price);
    }

    @Then("the alert status should be $status")
    public void theAlertStatusShouldBe(String status) {
        assertThat(stock.getStatus().name(), equalTo(status));
    }

    @Then("the trader sells all stocks")
    @Aliases(values={"the trader liquidates stocks"})
    public void theTraderSellsAllStocks() {
        trader.sellAllStocks();
        assertThat(trader.getStocks().size(), equalTo(0));
    }

}
