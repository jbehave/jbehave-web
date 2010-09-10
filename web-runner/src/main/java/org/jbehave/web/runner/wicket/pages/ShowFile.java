package org.jbehave.web.runner.wicket.pages;

import java.io.File;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.jbehave.web.io.ResourceFinder;

public class ShowFile extends Template {

	public ShowFile(File file) {
		ResourceFinder resourceFinder = new ResourceFinder("");
		add(new MultiLineLabel("fileContent", resourceFinder.resourceAsString(file.getPath())));
	}

}
