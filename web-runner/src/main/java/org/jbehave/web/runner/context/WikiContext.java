package org.jbehave.web.runner.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.core.io.rest.Resource;
import org.jbehave.web.runner.wicket.tree.TreeResource;

@SuppressWarnings("serial")
public class WikiContext implements Serializable {

	private String uri;
	private List<SerializableResource> resources;
	private List<TreeResource> treeRoots = new ArrayList<TreeResource>();

	public WikiContext() {
	}

	public List<SerializableResource> getResources() {
		Collections.sort(resources, new Comparator<SerializableResource>() {
			public int compare(SerializableResource o1, SerializableResource o2) {
				return o1.getUri().compareTo(o2.getUri());
			}
		});
		return resources;
	}

	public void setIndex(Map<String, Resource> index) {
		toList(index);
	}

	public List<TreeResource> getTreeRoots() {
		return treeRoots;
	}

	public void setTreeRoots(List<TreeResource> treeResources) {
		this.treeRoots = treeResources;		
	}

	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

    private void toList(Map<String, Resource> index) {
		resources = new ArrayList<SerializableResource>();
        for (Resource resource : index.values()) {
            List<Resource> ancestors = new ArrayList<Resource>();
            collectAncestors(ancestors, resource, index);
			resources.add(new SerializableResource(resource, toSerialisable(ancestors)));
        }
    }

    private void collectAncestors(List<Resource> ancestors, Resource resource, Map<String, Resource> index) {
        if (resource.hasParent()) {
            String parentName = resource.getParentName();
            Resource parent = index.get(parentName);
            if (parent != null) {
                ancestors.add(0, parent);
                collectAncestors(ancestors, parent, index);
            }
        }
    }

    private List<SerializableResource> toSerialisable(List<Resource> resources){
		List<SerializableResource> list = new ArrayList<SerializableResource>();
        for (Resource resource : resources) {
        	list.add(new SerializableResource(resource));
        }
        return list;    	
    }
    
	public static class SerializableResource implements Serializable,
			Comparable<SerializableResource> {

		private String name;
		private String uri;
		private List<SerializableResource> ancestors;

		public SerializableResource(Resource resource) {
			this(resource, null);
		}

		public SerializableResource(Resource resource, List<SerializableResource> ancestors) {
			this.ancestors = ( ancestors == null ? Arrays.<SerializableResource>asList() : ancestors ) ;
			this.name = resource.getName();
			this.uri = resource.getURI();
		}

		public String getName() {
			return name;
		}
		
		public boolean hasAncestors() {
			return !ancestors.isEmpty();
		}
		
		public String getPath() {
			List<String> names = new ArrayList<String>();
			for ( SerializableResource resource : ancestors){
				names.add(resource.getName());
			}
			names.add(name);
			return StringUtils.join(names, "/");
		}

		public String getUri() {
			return uri;		
		}
		
		public List<SerializableResource> getAncestors(){
			return ancestors;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

		public int compareTo(SerializableResource o) {
			return this.compareTo(o);
		}

	}

}
