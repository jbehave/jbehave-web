package org.jbehave.web.runner.waffle;

import org.codehaus.waffle.testing.registrar.RegistrarHelper;
import org.junit.Test;

import static org.codehaus.waffle.context.ContextLevel.*;
import static org.junit.Assert.assertNotNull;

public class JBehaveRegistrarTest {

    private static final Class<JBehaveRegistrar> CLASS = JBehaveRegistrar.class;

    @Test
    public void canRegisterComponentsAtDifferentLevels() {
        RegistrarHelper helper = new RegistrarHelper();
        helper.componentsFor(CLASS, APPLICATION);
        helper.componentsFor(CLASS, SESSION);
        helper.componentsFor(CLASS, REQUEST);
    }

    @Test
    public void canRetrieveControllers() {
        RegistrarHelper helper = new RegistrarHelper();
        assertNotNull(helper.controllerFor(CLASS, APPLICATION, "data/files"));
        assertNotNull(helper.controllerFor(CLASS, SESSION, "story/run"));
        assertNotNull(helper.controllerFor(CLASS, SESSION, "steps/find"));
        assertNotNull(helper.controllerFor(CLASS, REQUEST, "data/upload"));
    }
}
