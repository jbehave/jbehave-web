package org.jbehave.web.selenium;

import com.thoughtworks.xstream.XStream;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import java.util.Map;

/**
 * A {@link Format} that uses {@link SauceContextStoryReporter}.
 */
public class SauceContextOutput extends Format {

    private final WebDriverProvider webDriverProvider;
    private final SeleniumContext seleniumContext;
    private final java.util.Map<String, String> storyToSauceUrlMap;

    public SauceContextOutput(WebDriverProvider webDriverProvider, SeleniumContext seleniumContext, java.util.Map<String, String> storyToSauceUrlMap) {
        super("SAUCE_CONTEXT");
        this.webDriverProvider = webDriverProvider;
        this.seleniumContext = seleniumContext;
        this.storyToSauceUrlMap = storyToSauceUrlMap;
    }

    @Override
    public StoryReporter createStoryReporter(FilePrintStreamFactory filePrintStreamFactory,
                                             StoryReporterBuilder storyReporterBuilder) {
        return new SauceContextStoryReporter(webDriverProvider, seleniumContext, storyToSauceUrlMap);
    }

    public static class SauceLabsXRefRoot extends CrossReference.XRefRoot {

        protected final Map<String, String> storyToSauceUrlMap;

        public SauceLabsXRefRoot(java.util.Map<String, String> storyToSauceUrlMap) {
            this.storyToSauceUrlMap = storyToSauceUrlMap;
        }

        @Override
        protected CrossReference.XRefStory createXRefStory(StoryReporterBuilder storyReporterBuilder, Story story, boolean passed) {
            return new SauceLabsXRefStory(story, storyReporterBuilder, passed, storyToSauceUrlMap.get(story.getPath()));
        }
    }

    public static class SauceLabsXRefStory extends CrossReference.XRefStory {

        private String sauceLabsUrl;

        public SauceLabsXRefStory(Story story, StoryReporterBuilder storyReporterBuilder, boolean passed, String sauceLabsUrl) {
            super(story, storyReporterBuilder, passed);
            this.sauceLabsUrl = sauceLabsUrl;
        }
    }

    public static class SauceLabsCrossReference extends CrossReference {

        protected final Map<String, String> storyToSauceUrlMap;

        public SauceLabsCrossReference(Map<String, String> storyToSauceUrlMap) {
            this.storyToSauceUrlMap = storyToSauceUrlMap;
        }

        @Override
        protected XRefRoot newXRefRoot() {
            return new SauceContextOutput.SauceLabsXRefRoot(storyToSauceUrlMap);
        }

        @Override
        protected void aliasForXRefRoot(XStream xstream) {
            xstream.alias("xref", SauceContextOutput.SauceLabsXRefRoot.class);
        }

        @Override
        protected void aliasForXRefStory(XStream xstream) {
            xstream.alias("story", SauceContextOutput.SauceLabsXRefStory.class);
        }

    }


}
