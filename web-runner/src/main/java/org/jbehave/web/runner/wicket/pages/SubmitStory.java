package org.jbehave.web.runner.wicket.pages;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.model.Story;
import org.jbehave.web.runner.context.StoryContext;

import com.google.inject.Inject;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;
import static org.jbehave.core.reporters.Format.TXT;

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
                .withFormats(TXT);
    }

    public final class StoryForm extends Form<ValueMap> {
        public StoryForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextArea<String>("input", new PropertyModel<String>(storyContext, "input")).setType(String.class));
            add(new TextArea<String>("metaFilter", new PropertyModel<String>(storyContext, "metaFilter")).setType(String.class));
            add(new Button("runButton"));
            add(new BookmarkablePageLink<String>("viewLink", ViewStory.class, new PageParameters()));            
        }

        @Override
        public final void onSubmit() {
            run();
            BookmarkablePageLink<?> link = (BookmarkablePageLink<?>) get("viewLink");
            link.setBody(new Model<String>(storyContext.getOutput()));
            link.getPageParameters().set("id", storyContext.getOutput());
        }
    }

    private void run() {
        if (isNotBlank(storyContext.getInput())) {
            embedder.useMetaFilters(asList(storyContext.getMetaFilter()));
            String storyPath = storyPath();
            Story story = storyManager.storyOfText(storyContext.getInput(), storyPath);
            storyManager.runningStories(asList(story), embedder.metaFilter(), null);
            storyContext.setOutput(storyPath);
        }
    }

    private String storyPath() {
        return "web-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(System.currentTimeMillis()))
                + ".story";
    }

}
