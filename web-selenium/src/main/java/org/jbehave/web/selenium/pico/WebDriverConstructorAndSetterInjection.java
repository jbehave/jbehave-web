package org.jbehave.web.selenium.pico;

import groovy.lang.MetaClass;
import org.openqa.selenium.WebElement;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.ComponentMonitor;
import org.picocontainer.LifecycleStrategy;
import org.picocontainer.Parameter;
import org.picocontainer.PicoCompositionException;
import org.picocontainer.injectors.CompositeInjection;
import org.picocontainer.injectors.ConstructorInjection;
import org.picocontainer.injectors.SetterInjection;
import org.picocontainer.injectors.SetterInjector;

import java.lang.reflect.Method;
import java.util.Properties;

@SuppressWarnings("serial")
public class WebDriverConstructorAndSetterInjection extends CompositeInjection {
    public WebDriverConstructorAndSetterInjection() {
        super(new ConstructorInjection(),
                new SetterInjection() {
                    @Override
                    public <T> ComponentAdapter<T> createComponentAdapter(ComponentMonitor monitor, LifecycleStrategy lifecycleStrategy, Properties componentProperties, Object componentKey, Class<T> componentImplementation, Parameter... parameters) throws PicoCompositionException {
                        return new SetterInjector<T>(componentKey, componentImplementation, parameters, monitor, "set", "", false, true) {
                            @Override
                            protected boolean isInjectorMethod(Method method) {
                                Class<?> type = method.getParameterTypes()[0];
                                // WebElements as properties, are not injected by PicoContainer.
                                // See http://code.google.com/p/selenium/wiki/PageFactory
                                // Similarly, there are some inner-class properties made by Groovy's
                                // compiler that should not be injected.
                                if (type.equals(WebElement.class) || type.getName().matches("^.*Component$")) {
                                    return false;
                                }
                                // Groovy classes have a setMetaClass(MetaClass mc) method which we don't want to inject into.
                                return type != MetaClass.class && super.isInjectorMethod(method);
                            }
                        };
                    }
                });
    }
}
