package org.jbehave.web.runner.wicket.pages;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.time.Duration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.embedder.StoryManager.StoryStatus;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ViewStories extends Template {

    @Inject
    private Embedder embedder;

    private StoryManager storyManager;

    public ViewStories() {
        storyManager = embedder.storyManager();
        setPageTitle("ViewStories");
        @SuppressWarnings("rawtypes")
        IModel statusList = new LoadableDetachableModel() {
            protected Object load() {
                return storyManager.list();
            }
        };

        @SuppressWarnings("unchecked")
        ListView<StoryStatus> view = new ListView<StoryStatus>("storyStatus", statusList) {
            protected void populateItem(final ListItem<StoryStatus> item) {
                StoryStatus status = (StoryStatus) item.getModelObject();
                item.add(new Label("path", status.getPath()));
                item.add(new Label("done", status.isDone().toString()));
                item.add(new Label("failed", status.isFailed().toString()));
            }
        };

        // encapsulate ListView in a WebMarkupContainer to allow update
        WebMarkupContainer container = new WebMarkupContainer("storyContainer");
        // generate a markup-id so the contents can be updated through an AJAX
        container.setOutputMarkupId(true);
        container.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
        // add the view to the container
        container.add(view);
        // finally add the container to the page
        add(container);
    }

}
