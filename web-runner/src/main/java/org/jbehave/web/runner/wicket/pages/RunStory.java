package org.jbehave.web.runner.wicket.pages;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.web.runner.context.StoryContext;
import org.jbehave.web.runner.context.StoryOutputStream;

import com.google.inject.Inject;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@SuppressWarnings("serial")
public class RunStory extends Template {

    @Inject
    private Embedder embedder;

    private StoryManager storyManager;

    private StoryOutputStream outputStream = new StoryOutputStream();

    private StoryContext storyContext = new StoryContext();

    public RunStory() {
        reportTo(outputStream);
        storyManager = embedder.storyManager();
        setPageTitle("Run Story");
        add(new StoryForm("storyForm"));
    }

    public final class StoryForm extends Form<ValueMap> {
        public StoryForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextArea<String>("input", new PropertyModel<String>(storyContext, "input")).setType(String.class));
            add(new TextArea<String>("metaFilter", new PropertyModel<String>(storyContext, "metaFilter"))
                    .setType(String.class));
            add(new NoMarkupMultiLineLabel("output", new PropertyModel<String>(storyContext, "output"), "brush: plain"));
            add(new NoMarkupMultiLineLabel("failure", new PropertyModel<String>(storyContext, "failureStackTrace"),
                    "brush: java; gutter: false; collapse: true"));
            add(new Button("runButton"));
        }

        @Override
        public final void onSubmit() {
            run();
            if ( !storyContext.getFailureMessages().isEmpty() ){
                ((MultiLineLabel) get("failure")).setVisible(true);
            }
        }
    }

    private void reportTo(OutputStream ouputStream) {
        final Properties outputPatterns = new Properties();
        final Keywords keywords = embedder.configuration().keywords();
        final boolean reportFailureTrace = false;
        embedder.configuration().useStoryReporterBuilder(new StoryReporterBuilder() {
            @Override
            public StoryReporter build(String storyPath) {
                return new TxtOutput(new PrintStream(outputStream), outputPatterns, keywords, reportFailureTrace);
            }
        });
    }

    public void run() {
        if (isNotBlank(storyContext.getInput())) {
            outputStream.reset();
            storyContext.clearFailureCause();
            embedder.useMetaFilters(asList(storyContext.getMetaFilter()));
            String storyPath = storyPath();
            Story story = storyManager.storyOfText(storyContext.getInput(), storyPath);
            storyManager.runningStories(asList(story), embedder.metaFilter(), null);
            BatchFailures failures = new BatchFailures();
            storyManager.waitUntilAllDoneOrFailed(failures);
            if (!failures.isEmpty()) {
                storyContext.runFailedFor(failures.values().iterator().next());
            }
            storyContext.setOutput(outputStream.toString());
        }
    }

    private String storyPath() {
        return "web-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(System.currentTimeMillis()));
    }
}
