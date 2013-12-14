package org.jbehave.web.runner.wicket.pages;

import java.util.ArrayList;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.io.rest.Resource;
import org.jbehave.core.io.rest.ResourceIndexer;
import org.jbehave.web.runner.context.WikiContext;
import org.jbehave.web.runner.context.WikiContext.SerializableResource;
import org.jbehave.web.runner.wicket.WikiConfigurer;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class IndexWiki extends Template {

	@Inject
	private ResourceIndexer indexer;
	
	@Inject
	private WikiConfigurer configurer;
	
	private WikiContext wikiContext = new WikiContext();

	public IndexWiki() {
		setPageTitle("Wiki Index");
		add(new IndexForm("indexForm"));
	}

	public final class IndexForm extends Form<ValueMap> {
		public IndexForm(final String id) {
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
			setMarkupId("indexForm");
			add(new Label("uri", configurer.getURI()));
			add(new Button("updateButton"));
			add(new PropertyListView<SerializableResource>("resourcesList", new ArrayList<SerializableResource>()) {
				@Override
				public void populateItem(final ListItem<SerializableResource> item) {
					final SerializableResource resource = (SerializableResource) item.getModelObject();
			        item.add(new ExternalLink("edit_resource", resource.getUri()));
			        item.add(new Link<SerializableResource>("view_resource") {
			            @Override
			            public void onClick() {
			                PageParameters pageParameters = new PageParameters();
			                pageParameters.add("name", resource.getName());
			                pageParameters.add("uri", resource.getUri());
							setResponsePage(ViewResource.class, pageParameters);
			            }
			        });
			        item.add(new Link<SerializableResource>("run_resource") {
			            @Override
			            public void onClick() {
			                PageParameters pageParameters = new PageParameters();
			                pageParameters.add("name", resource.getName());
			                pageParameters.add("uri", resource.getUri());
							setResponsePage(RunResource.class, pageParameters);
			            }
			        });
					item.add(new Label("name"));
				}
			}).setVersioned(false);
			indexResources();
		}

		@Override
		public final void onSubmit() {
			indexResources();
		}
		
		private void indexResources() {
			String uri = configurer.getURI();
			Map<String, Resource> resources = indexer.indexResources(uri);
			wikiContext.setResources(resources);		
			@SuppressWarnings("unchecked")
			PropertyListView<SerializableResource> view = (PropertyListView<SerializableResource>) get("resourcesList");
			view.setDefaultModel(new ListModel<SerializableResource>(wikiContext.getSerializableResources()));
		}
	}


}
