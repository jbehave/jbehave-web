package org.jbehave.web.queue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.Embedder.ThrowableStory;
import org.jbehave.core.failures.BatchFailures;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class WebQueueTest {

    @Test
    public void shouldStartAndStopWebQueue() {
        // Given
        Embedder embedder = new Embedder();
        BatchFailures batchFailures = new BatchFailures();
        List<Future<ThrowableStory>> futures = new ArrayList<Future<ThrowableStory>>();
        WebQueueConfiguration configuration = new WebQueueConfiguration();
        configuration.useNavigatorDirectory(new File(new File(this.getClass().getProtectionDomain().getCodeSource()
                .getLocation().getFile()).getParentFile().getParentFile(), "src/test/resources/navigator"));
        configuration.useNavigatorPage("navigator.html");
        configuration.usePort(8090);
        configuration.useWelcomeFile("run-story.html");
        WebQueue queue = new WebQueue(embedder, batchFailures, futures, configuration);
        // When
        queue.start();
        // Then
        WebDriver driver = new HtmlUnitDriver();
        String url = "http://localhost:"+configuration.port();
        driver.get(url);
        assertThat("Submit a Story", equalTo(driver.getTitle()));
        driver.get(url+"/"+configuration.welcomeFile());
        assertThat("Submit a Story", equalTo(driver.getTitle()));
        driver.get(url+"/"+configuration.navigatorPage());
        assertThat("Story Navigator", equalTo(driver.getTitle()));
        queue.stop();
    }

}
