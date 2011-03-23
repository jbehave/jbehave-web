package org.jbehave.web.runner.wicket.pages;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.TxtOutput;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.web.runner.context.StoryContext;
import org.jbehave.web.runner.context.StoryOutputStream;

import com.google.inject.Inject;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class RunStory extends Template {

    @Inject
    private StoryRunner storyRunner;
    @Inject
    private Configuration configuration;
    @Inject
    private List<CandidateSteps> steps;

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

    private void reportTo(OutputStream ouputStream) {
        final Properties outputPatterns = new Properties();
        outputPatterns.setProperty("beforeStory", "{0}\n");
        final Keywords keywords = configuration.keywords();
        final boolean reportFailureTrace = false;
        configuration.useStoryReporterBuilder(new StoryReporterBuilder() {
            @Override
            public StoryReporter build(String storyPath) {
                return new TxtOutput(new PrintStream(outputStream), outputPatterns, keywords, reportFailureTrace);
            }
        });
    }

    public void run() {
        if (isNotBlank(storyContext.getInput())) {
            try {
                outputStream.reset();
                storyContext.clearFailureCause();
                MetaFilter metaFilter = ( isNotBlank(storyContext.getMetaFilter()) ? new MetaFilter(storyContext.getMetaFilter()) : MetaFilter.EMPTY );
                storyRunner.run(configuration, steps, parseStory(storyContext.getInput()), metaFilter);
            } catch (Throwable e) {
                storyContext.runFailedFor(e);
            }
            storyContext.setOutput(outputStream.toString());
        }
    }

    private Story parseStory(String storyInput) {
        return configuration.storyParser().parseStory(storyInput);
    }

}
