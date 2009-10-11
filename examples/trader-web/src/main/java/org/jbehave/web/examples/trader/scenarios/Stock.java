package org.jbehave.web.examples.trader.scenarios;

import java.util.ArrayList;
import java.util.List;

public class Stock {

    public enum AlertStatus {
        ON, OFF
    };

    private double threshold;
    private List<Double> prices = new ArrayList<Double>();
    private AlertStatus status = AlertStatus.OFF;

    public Stock(double threshold) {
    	this.threshold = threshold;
	}

	public List<Double> getPrices() {
        return prices;
    }

    public void tradeAt(double price) {
        this.prices.add(price);
        if (price > threshold) {
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
