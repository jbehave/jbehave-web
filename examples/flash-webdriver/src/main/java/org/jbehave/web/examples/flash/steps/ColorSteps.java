package org.jbehave.web.examples.flash.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.examples.flash.pages.Colors;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class ColorSteps {

    private final Colors colorsPage;

    public ColorSteps(Colors colorsPage) {
        this.colorsPage = colorsPage;
    }

    @Given("a flash app")
    public void givenAFlashApp(){
        colorsPage.open();
        assertThat(colorsPage.percentLoaded(), equalTo(100));
    }
    
    @When("user clicks on square")
    public void whenUserClicksOnSquare(){
        colorsPage.clickOnSquare();
    }
    
    @When("user changes label to '$label'")
    public void whenUserChangesLabel(String label){
        colorsPage.changeLabel(label);
    }

    @Then("title is '$title'")
    public void thenTitleIs(String title){
        assertThat(colorsPage.getTitle(), equalTo(title));
    }

    @Then("label is '$label'")
    public void thenLabelIs(String label){
        assertThat(colorsPage.getSquareLabel(), equalTo(label));
    }

    @Then("color is $color")
    public void thenColorIs(String color){
        assertThat(colorsPage.getColor(), equalTo(color));
    }

}

