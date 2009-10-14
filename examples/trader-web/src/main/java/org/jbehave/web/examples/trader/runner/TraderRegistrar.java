package org.jbehave.web.examples.trader.runner;

import java.io.File;

import org.codehaus.waffle.registrar.Registrar;
import org.jbehave.web.examples.trader.scenarios.StockExchangeSteps;
import org.jbehave.web.examples.trader.scenarios.TraderSteps;
import org.jbehave.web.runner.waffle.JBehaveRegistrar;

public class TraderRegistrar extends JBehaveRegistrar {

	public TraderRegistrar(Registrar delegate) {
		super(delegate);
	}

	@Override
	protected void registerSteps() {
		registerInstance(new TraderSteps());
		registerInstance(new StockExchangeSteps());
	}
	
	protected File uploadDirectory() {
		return new File("/tmp/upload");
	}
}
