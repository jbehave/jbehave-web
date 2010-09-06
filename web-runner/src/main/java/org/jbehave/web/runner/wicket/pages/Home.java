package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.markup.html.basic.Label;

public class Home extends Template {

    public Home() {
        setPageTitle("Welcome");
        add(new Label("welcome", "Welcome to JBehave Web Runner"));
    }
}
