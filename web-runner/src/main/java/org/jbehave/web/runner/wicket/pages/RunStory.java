package org.jbehave.web.runner.wicket.pages;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.web.runner.context.StoryContext;
import org.jbehave.web.runner.context.StoryOutputStream;

import com.google.inject.Inject;

public class RunStory extends Template {

    @Inject
    private Embedder storyRunner;
    @Inject
    private Configuration configuration;
    @Inject
    private List<CandidateSteps> steps;
    @Inject
    ExecutorService executorService;
    @Inject
    private EmbedderControls embedderControls;
    @Inject
    private List<Future<Throwable>> futures;

    private StoryOutputStream outputStream = new StoryOutputStream();
    private StoryContext storyContext = new StoryContext();

    public RunStory() {
        reportTo(outputStream);
        setPageTitle("Run Story");
        add(new StoryForm("storyForm"));
    }

    @SuppressWarnings("serial")
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

    private void reportTo(OutputStream outputStream) {
        final Properties outputPatterns = new Properties();
        outputPatterns.setProperty("beforeStory", "{0}\n");
        final Keywords keywords = configuration.keywords();
        final boolean reportFailureTrace = false;
        configuration.useDefaultStoryReporter(new TxtOutput(new PrintStream(this.outputStream), outputPatterns, keywords,
                reportFailureTrace));
    }

    public void run() {
        if (isNotBlank(storyContext.getInput())) {
            outputStream.reset();
            storyContext.clearFailureCause();
            MetaFilter metaFilter = MetaFilter.EMPTY;
            if (isNotBlank(storyContext.getMetaFilter())) {
                metaFilter = new MetaFilter(storyContext.getMetaFilter());
            }
            BatchFailures batchFailures = new BatchFailures();

            Future<Throwable> future = storyRunner.enqueueStory(executorService, embedderControls, configuration, steps, batchFailures,
                    metaFilter, futures, "" + System.currentTimeMillis(), parseStory(storyContext.getInput()));

            while (!future.isDone()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }

            try {
                if (future.get() != null) {
                    storyContext.runFailedFor(future.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            storyContext.setOutput(outputStream.toString());
        }
    }

    private Story parseStory(String storyInput) {
        return configuration.storyParser().parseStory(storyInput);
    }

}
