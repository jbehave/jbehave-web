package org.jbehave.web.selenium;

import org.jbehave.core.model.Story;
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
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceAccessKey;
import static org.jbehave.web.selenium.SauceWebDriverProvider.getSauceUser;

/**
 * A {@link StoryReporter} that passes back to SauceLabs the executed job results.
 */
public class SauceContextStoryReporter extends SeleniumContextStoryReporter {

    private final WebDriverProvider webDriverProvider;

    private ThreadLocal<String> storyName = new ThreadLocal<String>();
    private ThreadLocal<SessionId> sessionIds = new ThreadLocal<SessionId>();
    private ThreadLocal<Boolean> passed = new ThreadLocal<Boolean>();

    private Map<String, String> storyToJobIds = new HashMap<String, String>();

    private static final Pattern SAUCE_LABS_VIDEO_URL_PATTERN = Pattern.compile("http.*\\.flv");

    public SauceContextStoryReporter(WebDriverProvider webDriverProvider, SeleniumContext seleniumContext, java.util.Map<String, String> storyToSauceUrlMap) {
        super(seleniumContext);
        this.webDriverProvider = webDriverProvider;
        this.storyToJobIds = storyToSauceUrlMap;
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        storyName.set(story.getName());
        passed.set(true);
    }

    @Override
    public void beforeScenario(String title) {
        // This should really be done per Story, but the webDriverProvider has not done it's thing for this thread yet :-(
        sessionIds.set(((RemoteWebDriver) webDriverProvider.get()).getSessionId());
        String payload = "{\"tags\":[" + getJobTags() + "], " + getBuildId() + "\"name\":\" " + getJobName() + "\"}";
        postJobUpdate(storyName.get(), sessionIds.get(), payload);
        super.beforeScenario(title);
    }

    @Override
    public void failed(String step, Throwable cause) {
        passed.set(false);
    }

    @Override
    public void afterStory(boolean givenStory) {

        String storyName = this.storyName.get();
        if (storyName.equals("BeforeStories")
                || storyName.equals("AfterStories")
                || storyName.equals("BeforeStory")
                || storyName.equals("AfterStory")
                || storyName.equals("BeforeScenario")
                || storyName.equals("AfterScenario")) {
            return;
        }

        SessionId sessionId = sessionIds.get();

        if (sessionId == null ) {
            // no executed scenarios, as (most likely) excluded
            return;
        }

        boolean pass = passed.get().equals(true);
        String payload = "{ \"passed\":" + pass + "}";
        postJobUpdate(storyName, sessionId, payload);
    }

    private void postJobUpdate(String storyName, SessionId sessionId, String payload) {
        try {

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
            String jobUrl = null;
            if (rc == 200) {
                jobUrl = readResponseLinesFromSauceLabToGetJobUrl(new BufferedReader(new InputStreamReader(connection.getInputStream())));
                System.out.println("Saucelabs Job URL for " + (passed.get() ? "passing" : "failing") + " '" + storyName + "' : " + jobUrl);
                storyToJobIds.put(storyName, jobUrl);

            }
        } catch (IOException e) {
            System.err.println("Error updating Saucelabs job info: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected String readResponseLinesFromSauceLabToGetJobUrl(BufferedReader reader) throws IOException {
        String jobUrl = "";
        String responseLineFromSauceLabs;
        while ((responseLineFromSauceLabs = reader.readLine()) != null) {
            jobUrl = jobUrl + processSauceLabsResponseLine(responseLineFromSauceLabs);
        }
        return jobUrl;
    }

    /**
     * By deault, this prints a URL to the Job on SauceLabs.
     * Refer https://saucelabs.com/docs/sauce-ondemand
     * @param responseLineFromSauceLabs a line from the response
     */
    protected String processSauceLabsResponseLine(String responseLineFromSauceLabs) {
        String jobUrl = "";
        // This comes back from Saucelabs:
        // "video_url": "http://saucelabs.com/jobs/3bd32831ec0d91c423552330b332a59c4/video.flv",
        Matcher matcher = SAUCE_LABS_VIDEO_URL_PATTERN.matcher(responseLineFromSauceLabs);
        while (matcher.find()) {
            jobUrl = matcher.group().replace("/video.flv", "");
        }
        return jobUrl;
    }

    private String getBuildId() {
        String buildId =  System.getProperty("BUILD-ID");
        if (buildId != null) {
            return " \"build\":\"" + buildId + "\",";
        }
        return "";
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
