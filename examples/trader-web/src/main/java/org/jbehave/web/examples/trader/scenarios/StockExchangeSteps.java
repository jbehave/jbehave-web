package org.jbehave.web.examples.trader.scenarios;

import static org.junit.Assert.assertNotNull;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;

public class StockExchangeSteps extends Steps {

    private String stockExchange;

	@Given("a stock exchange $stockExchange")
    public void aStockExchange(String stockExchange){
		this.stockExchange = stockExchange;
    }

	@When("the stock exchange is opened")
	public void theStockExchangeIsOpened(){
		assertNotNull(stockExchange);
	}

}
