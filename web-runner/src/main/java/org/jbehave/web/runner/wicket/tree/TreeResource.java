package org.jbehave.web.runner.wicket.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.io.rest.Resource;

@SuppressWarnings("serial")
public class TreeResource implements Serializable {

	private String name;
	private String parentName;
	private String uri;
	private TreeResource parent;
	private List<TreeResource> children = new ArrayList<TreeResource>();

	public TreeResource(Resource resource) {
		this.name = resource.getName();
		this.parentName = resource.getParentName();
		this.uri = resource.getURI();
	}

	public TreeResource getParent() {
		return parent;
	}

	public void setParent(TreeResource parent) {
		this.parent = parent;
		this.parent.children.add(this);
	}

	public List<TreeResource> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public String getParentName() {
		return parentName;
	}

	public String getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return name;
	}

}
