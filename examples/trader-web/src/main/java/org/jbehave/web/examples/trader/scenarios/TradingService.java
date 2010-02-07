package org.jbehave.web.examples.trader.scenarios;

public class TradingService {

    public Stock newStock(double threshold) {
        return new Stock(threshold);
    }
}
