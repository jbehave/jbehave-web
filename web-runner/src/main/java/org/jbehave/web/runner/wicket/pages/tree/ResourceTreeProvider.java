package org.jbehave.web.runner.wicket.pages.tree;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

@SuppressWarnings("serial")
public class ResourceTreeProvider implements ITreeProvider<TreeResource> {

	private List<TreeResource> roots;

	public ResourceTreeProvider(List<TreeResource> roots) {
		this.roots = roots;
	}

	public void detach() {
		// noop
	}

	public Iterator<TreeResource> getRoots() {
		return roots.iterator();
	}

	public boolean hasChildren(TreeResource resource) {
		return resource.getParent() == null
				|| !resource.getChildren().isEmpty();
	}

	public Iterator<TreeResource> getChildren(
			final TreeResource resource) {
		return resource.getChildren().iterator();
	}

	public IModel<TreeResource> model(TreeResource resource) {
		return new Model<TreeResource>(resource);
	}

}
