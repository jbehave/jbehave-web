package org.jbehave.web.runner.wicket.pages;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
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
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

@SuppressWarnings("serial")
public class RunStory extends Template {

    @Inject
    private Embedder embedder;

    private StoryManager storyManager;

    private StoryOutputStream outputStream = new StoryOutputStream();
    private StoryContext storyContext = new StoryContext();


    public RunStory() {
        storyManager = embedder.storyManager();
        reportTo(outputStream);
        setPageTitle("Run Story");
        add(new StoryForm("storyForm"));
    }

    public final class StoryForm extends Form<ValueMap> {
        public StoryForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextArea<String>("input").setType(String.class));
            add(new TextArea<String>("metaFilter").setType(String.class));
            add(new NoMarkupMultiLineLabel("output", "", "brush: plain"));
            add(new NoMarkupMultiLineLabel("failure", "", "brush: java; gutter: false; collapse: true"));
            add(new Button("runButton"));
        }

        @Override
        public final void onSubmit() {
            String input = (String) getModelObject().get("input");
            storyContext.setInput(input);
            String metaFilter = (String) getModelObject().get("metaFilter");
            storyContext.setMetaFilter(metaFilter);
            run();
            MultiLineLabel output = (MultiLineLabel) get("output");
            output.setDefaultModelObject(storyContext.getOutput());
            MultiLineLabel failure = (MultiLineLabel) get("failure");
            failure.setDefaultModelObject(storyContext.getFailureStackTrace());
            if (isBlank(storyContext.getFailureStackTrace())) {
                failure.setVisible(true);
            }
        }
    }

    private void reportTo(OutputStream ouputStream) {
        final Properties outputPatterns = new Properties();
        outputPatterns.setProperty("beforeStory", "{0}\n");
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
            String storyPath = "web-runner-"+System.currentTimeMillis();
            Story story = storyManager.storyOfText(storyContext.getInput(), storyPath);
            storyManager.runningStories(asList(story), embedder.metaFilter(), null);
            BatchFailures failures = new BatchFailures();
            storyManager.waitUntilAllDoneOrFailed(failures);
            if ( !failures.isEmpty() ){
                storyContext.runFailedFor(failures.values().iterator().next());
            }            
            storyContext.setOutput(outputStream.toString());
        }
    }

}
