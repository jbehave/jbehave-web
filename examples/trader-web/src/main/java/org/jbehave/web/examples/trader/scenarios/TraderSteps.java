package org.jbehave.web.examples.trader.scenarios;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;

import java.util.List;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;

public class TraderSteps extends Steps {

    private Stock stock;
    private Trader trader;

    @Given("a stock of prices $prices and a threshold of $threshold")
    public void aStockOfPrice(List<Double> prices, double threshold) {
        stock = new Stock(prices, threshold);
    }

    @When("the stock is traded at $price")
    public void theStockIsTradedAt(double price) {
        stock.tradeAt(price);
    }

    @Then("the alert status should be $status")
    public void theAlertStatusShouldBe(String status) {
        ensureThat(stock.getStatus().name(), equalTo(status));
    }

    @Then("the trader sells all stocks")
    public void theTraderSellsAllStocks() {
        trader.sellAllStocks();
        ensureThat(trader.getStocks().size(), equalTo(0));
    }

}
