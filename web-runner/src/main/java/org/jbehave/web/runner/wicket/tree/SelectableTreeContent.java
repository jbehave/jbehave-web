package org.jbehave.web.runner.wicket.tree;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IModel;

public class SelectableTreeContent {

	private ITreeProvider<TreeResource> provider;

	private IModel<TreeResource> selected;

	public SelectableTreeContent(ITreeProvider<TreeResource> provider) {
		this.provider = provider;
	}

	public void detach() {
		if (selected != null) {
			selected.detach();
		}
	}

	protected boolean isSelected(TreeResource resource) {
		IModel<TreeResource> model = provider.model(resource);

		try {
			return selected != null && selected.equals(model);
		} finally {
			model.detach();
		}
	}

	protected void select(TreeResource resource,
			AbstractTree<TreeResource> tree,
			final AjaxRequestTarget target) {
		if (selected != null) {
			tree.updateNode(selected.getObject(), target);

			selected.detach();
			selected = null;
		}

		selected = provider.model(resource);

		tree.updateNode(resource, target);
	}

	@SuppressWarnings("serial")
	public Component newContentComponent(String id,
			final AbstractTree<TreeResource> tree,
			IModel<TreeResource> model) {
		return new Folder<TreeResource>(id, tree, model) {
			protected boolean isClickable() {
				return true;
			}

			protected void onClick(AjaxRequestTarget target) {
				SelectableTreeContent.this.select(getModelObject(), tree,
						target);
			}

			protected boolean isSelected() {
				return SelectableTreeContent.this
						.isSelected(getModelObject());
			}
		};
	}
}