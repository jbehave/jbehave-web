package org.jbehave.web.selenium;

/**
 * ContextView allows the viewing of context-related messages.
 * The principal use case is to fill a gap of functionality passing 
 * from Selenium API (with Selenium#setContext()) to WebDriver API
 * where this functionality is not available.
 */
public interface ContextView {

    void show(String message);
    
    void close();
    
    public static class NULL implements ContextView {
        public void show(String message) {
        }

        public void close() {
        }
    }

}
