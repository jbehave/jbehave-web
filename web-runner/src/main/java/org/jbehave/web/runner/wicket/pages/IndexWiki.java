package org.jbehave.web.runner.wicket.pages;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.util.resource.PackageResourceStream;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.velocity.markup.html.VelocityPanel;
import org.jbehave.core.io.rest.Resource;
import org.jbehave.core.io.rest.ResourceIndexer;
import org.jbehave.web.runner.context.WikiContext;
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
			add(new HtmlEscapingVelocityPanel("resources",
					new MapModel<String, Resource>(
							new HashMap<String, Resource>()),
					new PackageResourceStream(IndexWiki.class, "resources.vm"),
					"brush: plain"));
		}

		@Override
		public final void onSubmit() {
			indexResources();
			updatePanels();
		}

		private void indexResources() {
			String uri = configuration.getURI();
			Map<String, Resource> resources = indexer.indexResources(uri);
			wikiContext.setResources(resources);
		}

		private void updatePanels() {
			updateResourcesPanel();
		}

		private void updateResourcesPanel() {
			VelocityPanel panel = (VelocityPanel) get("resources");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("resources", wikiContext.getSerializableResources());
			panel.setDefaultModel(new MapModel<String, Object>(map));
		}

	
	}

}
