package org.jbehave.web.examples.trader.steps;

public class TradingService {

    public Stock newStock(double threshold) {
        return new Stock(threshold);
    }
}
