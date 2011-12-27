package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.containsString;

public class SubmitStoryTest extends TemplateTest {

    private Class<SubmitStory> pageClass = SubmitStory.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);        
    }

    @Test
    public void shouldSubmitStoryAndReportIdAsOutput() {
        tester.startPage(pageClass);
        FormTester formTester = tester.newFormTester("storyForm");
        String storyAsText = "Given a step\nWhen step is executed\nThen step is successful";
        formTester.setValue("input", storyAsText);
        formTester.submit("runButton");
        BookmarkablePageLink<?> link = (BookmarkablePageLink<?>) formTester.getForm().get("viewLink");
        assertThat(link.getBody().toString(), containsString("web-"));
    }

}
