package org.jbehave.web.waffle;

import static org.codehaus.waffle.context.ContextLevel.APPLICATION;
import static org.codehaus.waffle.context.ContextLevel.REQUEST;
import static org.codehaus.waffle.context.ContextLevel.SESSION;
import static org.junit.Assert.assertNotNull;

import org.codehaus.waffle.testing.registrar.RegistrarHelper;
import org.jbehave.web.waffle.JBehaveRegistrar;
import org.junit.Test;

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
        assertNotNull(helper.controllerFor(CLASS, SESSION, "scenario/scenario"));
        assertNotNull(helper.controllerFor(CLASS, REQUEST, "data/upload"));
    }
}
