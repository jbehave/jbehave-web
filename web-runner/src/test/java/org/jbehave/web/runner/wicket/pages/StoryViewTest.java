package org.jbehave.web.runner.wicket.pages;

import org.junit.Test;

public class StoryViewTest extends TemplateTest {

    private Class<StoryView> pageClass = StoryView.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);        
    }

}
