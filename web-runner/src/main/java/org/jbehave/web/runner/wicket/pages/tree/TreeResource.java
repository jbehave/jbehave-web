package org.jbehave.web.runner.wicket.pages.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
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
//		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
//				.append("name", name)
//				.append("parent", namesOf(parent))
//				.append("children",
//						namesOf(children.toArray(new TreeResource[0])))
//				.toString();
	}

	private String namesOf(TreeResource... resources) {
		List<String> names = new ArrayList<String>();
		for (TreeResource resource : resources) {
			if (resource != null) {
				names.add(resource.getName());
			}
		}
		return StringUtils.join(names, ",");
	}

}
