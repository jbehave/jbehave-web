package org.jbehave.web.runner.wicket.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import org.apache.wicket.util.tester.FormTester;
import org.jbehave.core.steps.StepType;
import org.jbehave.web.runner.context.StepdocContext.SerializableStepdoc;
import org.junit.Test;

public class FindStepsTest extends TemplateTest {

    private Class<FindSteps> pageClass = FindSteps.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);        
    }

    @Test
    public void shouldFindStepdocs() {
        // Given
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("stepsForm");
        // When
        String matchingStep = "Given a step";
        formTester.setValue("matchingStep", matchingStep);
        formTester.submit("findButton");
        // Then
        List<SerializableStepdoc> stepdocs = modelObject(formTester, "stepdocs");
        assertThat(stepdocs.size(), equalTo(1));
        SerializableStepdoc stepdoc = stepdocs.get(0);
        assertThat(stepdoc.getStepType(), equalTo(StepType.GIVEN));
        assertThat(stepdoc.getStartingWord(), equalTo("Given"));
        assertThat(stepdoc.getPattern(), equalTo("a step"));        
        assertThat(stepdoc.getMethodSignature(), equalTo(TestSteps.class.getName()+".givenAStep()"));
        assertThat(stepdoc.getStepsClass().getName(), equalTo(TestSteps.class.getName()));
    }

    @Test
    public void shouldFindStepsClasses() {
        //Given
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("stepsForm");
        // When
        formTester.submit("findButton");
        // Then
        List<Class<?>> stepsClasses = modelObject(formTester, "stepsInstances");
        assertThat(stepsClasses.size(), equalTo(1));
        assertThat(stepsClasses.get(0).getName(), equalTo(TestSteps.class.getName()));
    }

    @SuppressWarnings("unchecked")
    private <T> T modelObject(FormTester formTester, String key) {
        Map<String,T> model = (Map<String, T>) formTester.getForm().get(key).getDefaultModelObject();
        return model.get(key);
    }
    
}
