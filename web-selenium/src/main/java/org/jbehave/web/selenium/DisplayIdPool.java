package org.jbehave.web.selenium;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.Arrays.asList;

public class DisplayIdPool {

    private BlockingQueue<String> queue;

    public DisplayIdPool(String... displays) {
        queue = new ArrayBlockingQueue<String>(displays.length, false, asList(displays));
    }

    public String get() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnToPool(String displayId) {
        try {
            queue.put(displayId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
