package org.jbehave.web.runner.wicket.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.Format;
import org.jbehave.web.runner.context.StoryContext;

import com.google.inject.Inject;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;

@SuppressWarnings("serial")
public class SubmitStory extends Template {

    @Inject
    private Embedder embedder;

    private StoryManager storyManager;

    private StoryContext storyContext = new StoryContext();

    public SubmitStory() {
        reportToDirectory(System.getProperty("user.home") + "/jbehave");
        storyManager = embedder.storyManager();
        setPageTitle("Submit Story");
        add(new StoryForm("storyForm"));
    }

    private void reportToDirectory(String path) {
        embedder.configuration().storyReporterBuilder().withCodeLocation(codeLocationFromPath(path))
                .withFormats(Format.TXT);
    }

    public final class StoryForm extends Form<ValueMap> {
        public StoryForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextArea<String>("input").setType(String.class));
            add(new TextArea<String>("metaFilter").setType(String.class));
            add(new NoMarkupMultiLineLabel("output", "", "brush: plain"));
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
        }
    }

    public void run() {
        if (isNotBlank(storyContext.getInput())) {
            embedder.useMetaFilters(asList(storyContext.getMetaFilter()));
            String storyPath = storyPath();
            Story story = storyManager.storyOfText(storyContext.getInput(), storyPath);
            storyManager.runningStories(asList(story), embedder.metaFilter(), null);
            storyContext.setOutput(storyPath);
        }
    }

    private String storyPath() {
        return "web-runner-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(System.currentTimeMillis()))
                + ".story";
    }

}
