package org.jbehave.web.runner.wicket.pages;

import org.junit.Test;

public class HomeTest extends TemplateTest {

    @Test
    public void shouldRenderPage() {
        Class<Home> pageClass = Home.class;
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);
        tester.assertLabel("welcome", "Welcome to JBehave Web Runner");
    }

}
