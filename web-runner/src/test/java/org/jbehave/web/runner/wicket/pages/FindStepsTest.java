package org.jbehave.web.runner.wicket.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Collections;
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
    public void shouldFindASingleStep() {
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
    public void shouldFindAndSortSteps() {
        // Given
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("stepsForm");
        // When
        formTester.submit("findButton");
        // Then
        List<SerializableStepdoc> stepdocs = modelObject(formTester, "stepdocs");
        assertThat(stepdocs.size(), equalTo(6));

        // reset form
        formTester = tester.newFormTester("stepsForm");
        //TODO: following select causes NPE, but app works fine in interactive run
        //https://issues.apache.org/jira/browse/WICKET-3977
        //formTester.select("sortingSelect", 1);
        // When
        formTester.submit("findButton");
        // Then
        List<SerializableStepdoc> sorted = modelObject(formTester, "stepdocs");
        assertThat(sorted.size(), equalTo(6));
        // sort original stepdocs and compare with actual
        Collections.sort(stepdocs);
        for ( int i = 0; i < sorted.size(); i++ ){
            assertThat(sorted.get(i).getPattern(), equalTo(stepdocs.get(i).getPattern()));
        }
    }


    @Test
    public void shouldFindStepsInstances() {
        //Given
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("stepsForm");
        // When
        formTester.submit("findButton");
        // Then
        List<Class<?>> stepsClasses = modelObject(formTester, "stepsInstances");
        assertThat(stepsClasses.size(), equalTo(2));
        assertThat(stepsClasses.get(0).getName(), equalTo(TestSteps.class.getName()));
        assertThat(stepsClasses.get(1).getName(), equalTo(OtherTestSteps.class.getName()));
    }

    @SuppressWarnings("unchecked")
    private <T> T modelObject(FormTester formTester, String key) {
        Map<String,T> model = (Map<String, T>) formTester.getForm().get(key).getDefaultModelObject();
        return model.get(key);
    }
    
}
