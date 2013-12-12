package org.jbehave.web.runner.wicket.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.io.rest.Resource;
import org.jbehave.core.io.rest.ResourceIndexer;
import org.jbehave.web.runner.context.WikiContext;
import org.jbehave.web.runner.context.WikiContext.SerializableResource;
import org.jbehave.web.runner.wicket.WikiConfiguration;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class IndexWiki extends Template {

	@Inject
	private ResourceIndexer indexer;
	// @Inject
	private WikiConfiguration configuration = new WikiConfiguration(
			"http://demo.redmine.org/projects/jbehave-rest/wiki");

	private WikiContext wikiContext = new WikiContext();

	public IndexWiki() {
		setPageTitle("Wiki Index");
		add(new IndexForm("indexForm"));
	}

	public final class IndexForm extends Form<ValueMap> {
		public IndexForm(final String id) {
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
			setMarkupId("indexForm");
			add(new Label("uri", configuration.getURI()));
			add(new Button("updateButton"));
			add(new PropertyListView<SerializableResource>("resourcesList", new ArrayList<SerializableResource>()) {
				@Override
				public void populateItem(final ListItem<SerializableResource> listItem) {
					listItem.add(new Label("name"));
					listItem.add(new Label("uri"));
				}
			}).setVersioned(false);
		}

		@Override
		public final void onSubmit() {
			indexResources();
		}

		private void indexResources() {
			String uri = configuration.getURI();
			Map<String, Resource> resources = indexer.indexResources(uri);
			wikiContext.setResources(resources);		
			PropertyListView<SerializableResource> view = (PropertyListView<SerializableResource>) get("resourcesList");
			view.setDefaultModel(new ListModel<SerializableResource>(wikiContext.getSerializableResources()));
		}

	}

}
