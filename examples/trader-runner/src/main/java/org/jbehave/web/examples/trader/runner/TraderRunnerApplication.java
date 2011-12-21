package org.jbehave.web.examples.trader.runner;

import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.examples.trader.steps.StockExchangeSteps;
import org.jbehave.web.examples.trader.steps.TraderSteps;
import org.jbehave.web.examples.trader.steps.TradingService;
import org.jbehave.web.runner.wicket.WebRunnerApplication;

public class TraderRunnerApplication extends WebRunnerApplication {

    protected InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new TraderSteps(new TradingService()),
                new StockExchangeSteps());
    }

}
