package org.jbehave.web.selenium;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        sessionId.set(((RemoteWebDriverProvider.ScreenShottingRemoteWebDriver) webDriverProvider.get()).getSessionId());
    }

    @Override
    public void failed(String step, Throwable cause) {
        passed.set(false);
    }

    @Override
    public void afterStory(boolean givenStory) {
        if (sessionId.get() != null) {
            try {
                String payload = "'{\"tags\":[" + getTags() + "], \"passed\":\"" + passed.get() + "\",\"name\":\" " + storyName.get() + "\"}'";

                URL url = new URL("http://" + getSauceCredentials() + "@saucelabs.com/rest/v1/" + getSauceUser() + "/jobs/" + sessionId.get().toString());
                System.out.println("Sauce Job update URL: " + url.toString());
                System.out.println("Sauce Job update payload: " + payload);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(payload);
                writer.close();

                System.out.println("Sauce Job update return code: " + connection.getResponseCode());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("RemoteWebDriver reporting null session ID");
        }
    }

    private String getTags() {
        return "";
    }
}
