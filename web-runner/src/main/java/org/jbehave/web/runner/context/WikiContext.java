package org.jbehave.web.runner.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jbehave.core.io.rest.Resource;

@SuppressWarnings("serial")
public class WikiContext implements Serializable {

	private Map<String, Resource> resources;
	private String uri;

	public WikiContext() {
	}

	public List<SerializableResource> getSerializableResources(){
		List<SerializableResource> list = new ArrayList<SerializableResource>();
		for (Resource resource : resources.values()) {
			list.add(new SerializableResource(resource));
		}
		return list;
	}
	public Map<String, Resource> getResources() {
		return resources;
	}

	public void setResources(Map<String, Resource> index) {
		this.resources = index;
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

	public static class SerializableResource implements Serializable {

		private String name;
		private String uri;

		public SerializableResource(Resource resource) {
			this.name = resource.getName();
			this.uri = resource.getURI();
		}

		public String getName() {
			return name;
		}

		public String getUri() {
			return uri;
		}
				
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
