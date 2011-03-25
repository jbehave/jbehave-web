package org.jbehave.web.runner.wicket.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.Story;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.web.runner.context.StoryContext;

import com.google.inject.Inject;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class QueueStory extends Template {

    @Inject
    private Configuration configuration;
    @Inject
    private List<CandidateSteps> steps;

    private Embedder embedder = embedder();

    private BatchFailures batchFailures = new BatchFailures();
    private List<Future<Embedder.ThrowableStory>> futures = new ArrayList<Future<Embedder.ThrowableStory>>();
    private StoryContext storyContext = new StoryContext();

    public QueueStory() {
        setPageTitle("Queue Story");
        add(new StoryForm("storyForm"));
    }

    @SuppressWarnings("serial")
    public final class StoryForm extends Form<ValueMap> {
        public StoryForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextArea<String>("input").setType(String.class));
            add(new TextArea<String>("metaFilter").setType(String.class));
            add(new NoMarkupMultiLineLabel("output", "", "brush: plain"));
            add(new NoMarkupMultiLineLabel("futures", "", "brush: java; gutter: false; collapse: true"));
            add(new NoMarkupMultiLineLabel("batchFailures", "", "brush: java; gutter: false; collapse: true"));
            add(new Button("queueButton"));
        }

        @Override
        public final void onSubmit() {
            String input = (String) getModelObject().get("input");
            storyContext.setInput(input);
            String metaFilter = (String) getModelObject().get("metaFilter");
            storyContext.setMetaFilter(metaFilter);
            queue();
            MultiLineLabel output = (MultiLineLabel) get("output");
            output.setDefaultModelObject(storyContext.getOutput());
            MultiLineLabel futures = (MultiLineLabel) get("futures");
            futures.setDefaultModelObject(storyContext.getFutures());
            MultiLineLabel batchFailures = (MultiLineLabel) get("batchFailures");
            batchFailures.setDefaultModelObject(storyContext.getBatchFailures());
            if ( !storyContext.getBatchFailures().isEmpty() ) {
                batchFailures.setVisible(true);
            }
        }
    }

    public void queue() {
        if (isNotBlank(storyContext.getInput())) {
            String storyPath = "" + System.currentTimeMillis();
            Story story = embedder.configuration().storyParser().parseStory(storyContext.getInput(), storyPath);
            MetaFilter metaFilter = ( isNotBlank(storyContext.getMetaFilter()) ? new MetaFilter(storyContext.getMetaFilter()) : MetaFilter.EMPTY );
            embedder.enqueueStory(batchFailures, metaFilter, futures, storyPath, story);
            storyContext.setBatchFailures(batchFailures);
            storyContext.setOutput(storyPath);
        }
        storyContext.setFutures(futures);
    }

    private Embedder embedder() {
        Embedder embedder = new Embedder();
        embedder.useConfiguration(configuration);
        embedder.useCandidateSteps(steps);
        return embedder;
    }

}
