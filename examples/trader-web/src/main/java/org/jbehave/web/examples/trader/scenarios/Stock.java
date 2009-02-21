package org.jbehave.web.examples.trader.scenarios;

import java.util.List;

public class Stock {

    public enum AlertStatus {
        ON, OFF
    };

    private List<Double> prices;
    private double alertPrice;
    private AlertStatus status = AlertStatus.OFF;

    public Stock(List<Double> prices, double alertPrice) {
        this.prices = prices;
        this.alertPrice = alertPrice;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void tradeAt(double price) {
        this.prices.add(price);
        if (price > alertPrice) {
            status = AlertStatus.ON;
        }
    }

    public void resetAlert() {
        status = AlertStatus.OFF;
    }

    public AlertStatus getStatus() {
        return status;
    }

}
