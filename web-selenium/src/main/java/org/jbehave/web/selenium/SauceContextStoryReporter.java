package org.jbehave.web.selenium;

import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.IOException;

import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceCredentials;
import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceUser;

public class SauceContextStoryReporter extends NullStoryReporter {

    private final WebDriverProvider webDriverProvider;

    private ThreadLocal<String> storyName = new ThreadLocal<String>();
    private ThreadLocal<SessionId> sessionId = new ThreadLocal<SessionId>();
    private ThreadLocal<Boolean> passed = new ThreadLocal<Boolean>();

    public SauceContextStoryReporter(WebDriverProvider webDriverProvider) {
        this.webDriverProvider = webDriverProvider;
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        storyName.set(story.getName());
        passed.set(true);
    }

    @Override
    public void beforeScenario(String title) {
        sessionId.set(((RemoteWebDriver) webDriverProvider.get()).getSessionId());
    }

    @Override
    public void failed(String step, Throwable cause) {
        passed.set(false);
    }

    @Override
    public void afterStory(boolean givenStory) {
        if (sessionId.get() != null) {
            try {
                String contentType = "'Content-Type: application/json'";
                String curlString = "curl -v -X PUT http://" + getSauceCredentials() + "@saucelabs.com/rest/v1/" + getSauceUser() + "/jobs/"
                        + sessionId.get().toString()
                        + " -H " + contentType + " -d '{\"tags\":[" + getTags() + "], \"passed\":\"" + passed.get() + "\",\"name\":\" " + storyName.get() + "\"}'";
                System.out.println("---> " + curlString);
                Runtime.getRuntime().exec(curlString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("RemoteWebDriver reporting null session ID");
        }
    }

    private String getTags() {
        return "";
    }
}
