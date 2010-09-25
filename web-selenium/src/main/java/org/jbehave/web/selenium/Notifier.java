package org.jbehave.web.selenium;

public interface Notifier {
    void notify(String message);
    void close();
    public static class NULL implements Notifier {
        public void notify(String message) {
        }

        public void close() {
        }
    }
}
