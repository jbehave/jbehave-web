package org.jbehave.web.selenium;

import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceAccessKey;
import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceUser;

/**
 * A {@link StoryReporter} that passes back to SauceLabs the executed job results.
 */
public class SauceContextStoryReporter extends NullStoryReporter {

    private final WebDriverProvider webDriverProvider;

    private ThreadLocal<String> storyName = new ThreadLocal<String>();
    private ThreadLocal<SessionId> sessionIds = new ThreadLocal<SessionId>();
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
        sessionIds.set(((RemoteWebDriver) webDriverProvider.get()).getSessionId());
    }

    @Override
    public void failed(String step, Throwable cause) {
        passed.set(false);
    }

    @Override
    public void afterStory(boolean givenStory) {

        SessionId sessionId = sessionIds.get();

        if (sessionId == null ) {
            // no executed scenarios, as (most likely) excluded
            return;
        }

        try {
            String payload = "{\"tags\":[" + getJobTags() + "], \"passed\":\"" + passed.get() + "\",\"name\":\" " + getJobName() + "\"}";

            URL url = new URL("http://saucelabs.com/rest/v1/" + getSauceUser() + "/jobs/" + sessionId.toString());

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

            int rc = connection.getResponseCode();
            if (rc == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s;

                // This comes back from Saucelabs:
                // "video_url": "http://saucelabs.com/jobs/3bd32831ec0d91c423552330b332a59c4/video.flv",
                Pattern p = Pattern.compile("http.*\\.flv");
                while ((s = reader.readLine()) != null) {
                    Matcher matcher = p.matcher(s);
                    while (matcher.find()) {
                        System.out.println("Saucelabs Video: " + matcher.group());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating Saucelabs job info: " + e.getMessage());
            e.printStackTrace();
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
