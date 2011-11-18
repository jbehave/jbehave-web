package org.jbehave.web.selenium;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;

public class SauceLabsContextViewTest {

    private StringBuilder script = new StringBuilder();
    private boolean shouldBarf = false;

    private SauceLabsContextView slcv = new SauceLabsContextView(new WebDriverProvider() {
        public WebDriver get() {
            return new WebDriverPage(null) {
                @Override
                public Object executeScript(String s, Object... args) {
                    if (shouldBarf) {
                        throw new RuntimeException("Simulate Sauce Issue");
                    }
                    script.append(s);
                    return "";
                }
            };
        }

        public void initialize() {
        }

        public void end() {
        }

        public boolean saveScreenshotTo(String path) {
            return false;
        }
    });


    @Test
    public void simpleMessageShouldBePassedToSauceLabsUsingTheirEncoding() throws Exception {
        slcv.show("Scenario1", "Step1");
        slcv.show("Scenario1", "Step2");
        slcv.show("Scenario2", "Step1");
        assertThat(script.toString(), Matchers.equalTo("sauce:context=Step: Step1" +
                "sauce:context=Step: Step2" +
                "sauce:context=Step: Step1"));
    }

    @Test
    public void exceptionThrownBySauceLabsOrWebDriverShouldFailSilently() throws Exception {
        shouldBarf = true;
        slcv.show("boo!", "oops");
        assertThat(script.toString(), Matchers.equalTo(""));
    }

    @Test
    public void closingMessageShouldNotBePassedToSauceLabs() throws Exception {
        slcv.close();
        assertThat(script.toString(), Matchers.equalTo(""));
    }
}
