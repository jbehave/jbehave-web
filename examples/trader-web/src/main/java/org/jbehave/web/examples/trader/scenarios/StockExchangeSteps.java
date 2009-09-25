package org.jbehave.web.examples.trader.scenarios;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jbehave.scenario.annotations.AfterScenario;
import org.jbehave.scenario.annotations.BeforeScenario;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.web.io.ResourceFinder;

public class StockExchangeSteps extends Steps {

	private ResourceFinder resourceFinder = new ResourceFinder("classpath:org/jbehave/web/examples/trader/scenarios");
	
    private String stockExchange;
    
    @BeforeScenario
    public void beforeStockExchangeOpens(){
    	
    }

    @AfterScenario
    public void afterStockExchangeCloses(){
    	
    }

	@Given("a resource root directory $rootDirectory")
    public void aRootDirectory(String rootDirectory){
		this.resourceFinder.useRootDirectory(rootDirectory);
	}

	@Given("a stock exchange $stockExchange")
    public void aStockExchange(String stockExchange){
		this.stockExchange = stockExchange;
    }

	@When("the stock exchange is opened")
	public void theStockExchangeIsOpened(){
		assertNotNull(stockExchange);
	}
	
	@Then("the stock exchanges opened are as contained in $resource")
	public void theStockExchangesAreEqualTo(String resource){
		assertEquals(resourceFinder.resourceAsString(resource), stockExchange);
	}

}
