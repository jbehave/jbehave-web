package org.jbehave.web.runner.wicket.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

public class RunStoryTest extends TemplateTest {

    private Class<RunStory> pageClass = RunStory.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);        
    }

    @Test
    public void shouldRunStoryAndReportOutputForSuccessfulSteps() {
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("storyForm");
        String storyAsText = "Given a step\nWhen step is executed\nThen step is successful";
        formTester.setValue("input", storyAsText);
        formTester.submit("runButton");
        String output = formTester.getForm().get("output").getDefaultModelObjectAsString();
        assertThat(output, containsString(storyAsText));
    }

    @Test
    public void shouldRunStoryAndReportOutputForNonSuccessfulSteps() {
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("storyForm");
        String storyAsText = "Given a step\nWhen step is pending\nThen step is successful";
        formTester.setValue("input", storyAsText);
        formTester.submit("runButton");
        String output = formTester.getForm().get("output").getDefaultModelObjectAsString();
        assertThat(output, containsString("When step is pending (PENDING)"));
        assertThat(output, containsString("Then step is successful (NOT PERFORMED)"));
    }

    @Test
    public void shouldRunStoryWithMetaFilter() {
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("storyForm");
        String storyAsText = "Meta: @skip\nGiven a step\nWhen step is executed\nThen step is successful";
        formTester.setValue("input", storyAsText);
        formTester.setValue("metaFilter", "-skip");
        formTester.submit("runButton");
        String output = formTester.getForm().get("output").getDefaultModelObjectAsString();
        assertThat(output, containsString("-skip"));
    }

}
