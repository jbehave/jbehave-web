package org.jbehave.web.selenium;

import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import org.openqa.selenium.remote.SessionId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceAccessKey;
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
                String payload = "{\"tags\":[" + getJobTags() + "], \"passed\":\"" + passed.get() + "\",\"name\":\" " + getJobName() + "\"}";

                URL url = new URL("http://saucelabs.com/rest/v1/" + getSauceUser() + "/jobs/" + sessionId.get().toString());

                Authenticator.setDefault(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(getSauceUser(), getSauceAccessKey().toCharArray());
                    }
                });

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("PUT");

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(payload);
                writer.close();

                System.out.println("Sauce Job update return code: " + connection.getResponseCode());
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                System.err.println("Error " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("RemoteWebDriver reporting null session ID");
        }
    }

    /**
     * The name of the job. By default this is the story name.
     * @return the job name
     */
    protected String getJobName() {
        return storyName.get();
    }

    /**
     * A set of tags to apply to the job, like so:
     *   "foo", "bar"
     *
     * @return a string of comma separated strings in quotes
     */
    protected String getJobTags() {
        return "";
    }
}
