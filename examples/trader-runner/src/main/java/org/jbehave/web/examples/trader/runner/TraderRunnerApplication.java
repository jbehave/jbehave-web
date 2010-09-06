package org.jbehave.web.examples.trader.runner;

import java.util.List;

import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.examples.trader.steps.StockExchangeSteps;
import org.jbehave.web.examples.trader.steps.TraderSteps;
import org.jbehave.web.examples.trader.steps.TradingService;
import org.jbehave.web.runner.wicket.WebRunnerApplication;

public class TraderRunnerApplication extends WebRunnerApplication {

    protected List<CandidateSteps> candidateSteps() {
        return new InstanceStepsFactory(configuration(), new TraderSteps(new TradingService()),
                new StockExchangeSteps()).createCandidateSteps();
    }

}
