package org.jbehave.web.examples.trader;

import org.jbehave.core.annotations.When;

public class TraderWebSteps {

    @When("user looks for step: \"$step\"")
    public void whenStepsIsSeached(String step){
        System.out.println("Looking for: "+step);
    }
}
