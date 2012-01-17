package ${package}.steps;

import org.jbehave.web.selenium.PerStoryWebDriverSteps;
import org.jbehave.web.selenium.WebDriverProvider;

public class LifecycleSteps extends PerStoryWebDriverSteps {

    public LifecycleSteps(WebDriverProvider webDriverProvider) {
        super(webDriverProvider);
    }
}
