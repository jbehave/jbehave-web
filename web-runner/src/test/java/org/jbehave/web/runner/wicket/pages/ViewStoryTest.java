package org.jbehave.web.runner.wicket.pages;

import org.junit.Test;

public class ViewStoryTest extends TemplateTest {

    private Class<ViewStory> pageClass = ViewStory.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);        
    }

}
