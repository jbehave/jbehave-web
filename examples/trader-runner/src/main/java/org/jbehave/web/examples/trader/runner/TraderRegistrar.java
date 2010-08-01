package org.jbehave.web.examples.trader.runner;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.List;

import org.codehaus.waffle.registrar.Registrar;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.web.examples.trader.steps.StockExchangeSteps;
import org.jbehave.web.examples.trader.steps.TraderSteps;
import org.jbehave.web.examples.trader.steps.TradingService;
import org.jbehave.web.io.PrintStreamFileMonitor;
import org.jbehave.web.runner.waffle.JBehaveRegistrar;

public class TraderRegistrar extends JBehaveRegistrar {

    public TraderRegistrar(Registrar delegate) {
        super(delegate);
    }

    @Override
    protected void registerSteps() {
        List<Object> stepsInstances = asList(new TraderSteps(new TradingService()), new StockExchangeSteps());
        List<CandidateSteps> candidateSteps = new InstanceStepsFactory(new MostUsefulConfiguration(), stepsInstances).createCandidateSteps();
        for (CandidateSteps candidate : candidateSteps) {
            registerInstance(candidate);
        }
    }

    @Override
    protected void registerFileMonitor() {
        register(PrintStreamFileMonitor.class);
    }

    @Override
    protected File uploadDirectory() {
        // Can be overridden to return, e.g. new File("/tmp/upload");
        return super.uploadDirectory();
    }
}
