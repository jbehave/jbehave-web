package org.jbehave.web.examples.trader.runner;

import org.codehaus.waffle.registrar.Registrar;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.MostUsefulStepsConfiguration;
import org.jbehave.core.steps.StepsConfiguration;
import org.jbehave.core.steps.pico.PicoStepsFactory;
import org.jbehave.web.examples.trader.scenarios.StockExchangeSteps;
import org.jbehave.web.examples.trader.scenarios.TraderSteps;
import org.jbehave.web.examples.trader.scenarios.TradingService;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.ConstructorInjection;

public class PicoTraderRegistrar extends TraderRegistrar {

    public PicoTraderRegistrar(Registrar delegate) {
        super(delegate);
    }

    @Override
    protected void registerSteps() {
        for (CandidateSteps steps : createSteps()) {
            registerInstance(steps);
        }
    }

    protected CandidateSteps[] createSteps() {
        PicoContainer parent = createPicoContainer();
        return new PicoStepsFactory(new MostUsefulStepsConfiguration(), parent).createCandidateSteps();
    }

    private PicoContainer createPicoContainer() {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(TradingService.class);
        parent.as(Characteristics.USE_NAMES).addComponent(TraderSteps.class);
        parent.as(Characteristics.USE_NAMES).addComponent(StockExchangeSteps.class);
        return parent;
    }

}