package org.jbehave.web.examples.trader.waffle;

import org.codehaus.waffle.registrar.Registrar;
import org.jbehave.web.examples.trader.scenarios.TraderSteps;
import org.jbehave.web.waffle.JBehaveRegistrar;

public class TraderRegistrar extends JBehaveRegistrar {

	public TraderRegistrar(Registrar delegate) {
		super(delegate);
	}

	@Override
	protected void registerSteps() {
		registerInstance(new TraderSteps());
	}
	
}
