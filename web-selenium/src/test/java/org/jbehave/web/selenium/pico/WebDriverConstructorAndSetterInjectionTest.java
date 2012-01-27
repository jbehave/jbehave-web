package org.jbehave.web.selenium.pico;

import groovy.lang.MetaClass;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.picocontainer.DefaultPicoContainer;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static org.mockito.Mockito.mock;

public class WebDriverConstructorAndSetterInjectionTest {

    private WebDriverConstructorAndSetterInjection injection = new WebDriverConstructorAndSetterInjection();
    private DefaultPicoContainer mpc = new DefaultPicoContainer(injection);

    @Before
    public void setUp() throws Exception {
        mpc.addComponent("Hello");
        mpc.addComponent(mock(WebElement.class));
        mpc.addComponent(mock(MetaClass.class));
    }

    @Test
    public void constructorInjectionShouldBePossible() {
        WillDoThis result = mpc.addComponent(WillDoThis.class).getComponent(WillDoThis.class);
        assertThat(result.field, equalTo("Hello"));
    }

    @Test
    public void setterInjectionShouldBePossible() {
        WillDoThis2 result = mpc.addComponent(WillDoThis2.class).getComponent(WillDoThis2.class);
        assertThat(result.field, equalTo("Hello"));
    }

    @Test
    public void fieldInjectionShouldNotBeAllowed() {
        WontDoThis result = mpc.addComponent(WontDoThis.class).getComponent(WontDoThis.class);
        assertThat(result.field, nullValue());
    }

    @Test
    public void webElementShouldNotBeAllowed() {
        WontDoThis2 result = mpc.addComponent(WontDoThis2.class).getComponent(WontDoThis2.class);
        assertThat(result.field, nullValue());
    }

    @Test
    public void metaClassShouldNotBeAllowed() {
        WontDoThis3 result = mpc.addComponent(WontDoThis3.class).getComponent(WontDoThis3.class);
        assertThat(result.field, nullValue());
    }

    @Test
    public void groovyInnerClassPropertiesShouldNotBeAllowed() {
        WontDoThis4 result = mpc.addComponent(WontDoThis4.class)
                .addComponent(WontDoThis4Component.class)
                .getComponent(WontDoThis4.class);
        assertThat(result.field, nullValue());
    }

    public static class WontDoThis {
        public String field;
    }

    public static class WillDoThis {
        private String field;
        public WillDoThis(String field) {
            this.field = field;
        }
    }

    public static class WillDoThis2 {
        private String field;
        public void setField(String field) {
            this.field = field;
        }
    }

    public static class WontDoThis2 {
        private WebElement field;
        public void setField(WebElement field) {
            this.field = field;
        }
    }

    public static class WontDoThis3 {
        private MetaClass field;
        public void setField(MetaClass field) {
            this.field = field;
        }
    }

    public static class WontDoThis4 {
        private WontDoThis4Component field;
        public void setField(WontDoThis4Component field) {
            this.field = field;
        }
    }
    public static class WontDoThis4Component {
    }



}
