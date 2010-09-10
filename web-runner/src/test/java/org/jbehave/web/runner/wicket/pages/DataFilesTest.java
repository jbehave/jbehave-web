package org.jbehave.web.runner.wicket.pages;

import org.junit.Test;

public class DataFilesTest extends TemplateTest {

    private Class<DataFiles> pageClass = DataFiles.class;

    @Test
    public void shouldRenderPage() {
        tester.startPage(pageClass);
        tester.assertRenderedPage(pageClass);
    }

}
