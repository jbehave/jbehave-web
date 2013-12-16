package org.jbehave.web.runner.wicket.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.HumanTheme;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.value.ValueMap;
import org.jbehave.core.io.rest.Resource;
import org.jbehave.core.io.rest.ResourceIndexer;
import org.jbehave.web.runner.context.WikiContext;
import org.jbehave.web.runner.wicket.WikiConfigurer;
import org.jbehave.web.runner.wicket.tree.ResourceSet;
import org.jbehave.web.runner.wicket.tree.ResourceTreeProvider;
import org.jbehave.web.runner.wicket.tree.SelectableTreeContent;
import org.jbehave.web.runner.wicket.tree.TreeResource;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class WikiTree extends Template {

	@Inject
	private ResourceIndexer indexer;

	@Inject
	private WikiConfigurer configurer;

	private WikiContext wikiContext = new WikiContext();

	public WikiTree() {
		setPageTitle("Wiki Tree");
		add(new TreeForm("treeForm"));
	}

	public final class TreeForm extends Form<ValueMap> {
		private Behavior theme = new HumanTheme();
		private AbstractTree<TreeResource> tree;
		private ResourceTreeProvider provider;
		private SelectableTreeContent content;

		public TreeForm(final String id) {
			super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
			setMarkupId("treeForm");
			indexResources();
			provider = new ResourceTreeProvider(wikiContext.getTreeRoots());
			content = new SelectableTreeContent(provider);

			tree = createTree(provider, new ResourceModel());
			tree.add(new Behavior() {
				@Override
				public void onComponentTag(Component component, ComponentTag tag) {
					theme.onComponentTag(component, tag);
				}

				@Override
				public void renderHead(Component component,
						IHeaderResponse response) {
					theme.renderHead(component, response);
				}
			});
			add(tree);

			add(new Link<Void>("expandAll") {
				@Override
				public void onClick() {
					ResourceSet.get().expandAll();
				}
			});

			add(new Link<Void>("collapseAll") {
				@Override
				public void onClick() {
					ResourceSet.get().collapseAll();
				}
			});

			add(new Button("submit") {
				@Override
				public void onSubmit() {
				}
			});
			// add(new Label("uri", configurer.getURI()));
			// add(new Button("updateButton"));
		}

		@Override
		public final void onSubmit() {
			indexResources();
		}

		private void indexResources() {
			String uri = configurer.getURI();
			Map<String, Resource> index = indexer.indexResources(uri);
			wikiContext.setTreeRoots(treeRoots(treeResources(index)));
		}

		private Map<String, TreeResource> treeResources(
				Map<String, Resource> index) {
			Map<String, TreeResource> treeResources = new HashMap<String, TreeResource>();
			for (String name : index.keySet()) {
				treeResources.put(name, new TreeResource(index.get(name)));
			}
			return treeResources;
		}

		private List<TreeResource> treeRoots(Map<String, TreeResource> index) {
			List<TreeResource> treeResources = treeResourcesWithParent(index);
			List<TreeResource> treeRoots = new ArrayList<TreeResource>();
			for (TreeResource resource : treeResources) {
				if (resource.getParent() == null) {
					treeRoots.add(resource);
				}
			}
			return treeRoots;
		}

		private List<TreeResource> treeResourcesWithParent(
				Map<String, TreeResource> index) {
			List<TreeResource> resources = new ArrayList<TreeResource>();
			for (TreeResource resource : index.values()) {
				List<TreeResource> ancestors = new ArrayList<TreeResource>();
				addAncestors(ancestors, resource, index);
				if (ancestors.size() > 0) {
					resource.setParent(ancestors.get(0));
				}
				resources.add(resource);
			}
			return resources;
		}

		private void addAncestors(List<TreeResource> ancestors,
				TreeResource resource, Map<String, TreeResource> index) {
			String parentName = resource.getParentName();
			if (parentName != null) {
				TreeResource parent = index.get(parentName);
				if (parent != null) {
					ancestors.add(parent);
					addAncestors(ancestors, parent, index);
				}
			}
		}

		protected NestedTree<TreeResource> createTree(
				ResourceTreeProvider provider, IModel<Set<TreeResource>> state) {
			NestedTree<TreeResource> tree = new NestedTree<TreeResource>(
					"tree", provider, state) {
				protected Component newContentComponent(String id,
						IModel<TreeResource> model) {
					return content.newContentComponent(id, this, model);
				}
			};
			return tree;
		}

		@Override
		public void detachModels() {
			content.detach();
			super.detachModels();
		}
	}

	protected class ResourceModel extends
			AbstractReadOnlyModel<Set<TreeResource>> {
		@Override
		public Set<TreeResource> getObject() {
			return ResourceSet.get();
		}
	}
}
