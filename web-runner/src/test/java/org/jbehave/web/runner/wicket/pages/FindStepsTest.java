package org.jbehave.web.runner.wicket.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import org.apache.wicket.util.tester.FormTester;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.Stepdoc;
import org.junit.Test;

public class FindStepsTest extends TemplateTest {

    private Class<FindSteps> pageClass = FindSteps.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);        
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindStepdocs() {
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("stepsForm");
        String matchingStep = "Given a step";
        formTester.setValue("matchingStep", matchingStep);
        formTester.submit("findButton");
        Map<String, List<Stepdoc>> model = (Map<String, List<Stepdoc>>) formTester.getForm().get("stepdocs").getDefaultModelObject();
        List<Stepdoc> stepdocs = model.get("stepdocs");
        assertThat(stepdocs.size(), equalTo(1));
        Stepdoc stepdoc = stepdocs.get(0);
        assertThat(stepdoc.getStepType(), equalTo(StepType.GIVEN));
        assertThat(stepdoc.getPattern(), equalTo("a step"));
    }
    

}
