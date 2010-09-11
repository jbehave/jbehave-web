package org.jbehave.web.runner.wicket.pages;

import java.io.File;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.jbehave.web.io.ResourceFinder;

public class ViewFileContent extends Template {

	public ViewFileContent(File file) {
		add(new MultiLineLabel("fileContent", new ResourceFinder().resourceAsString(file.getPath())));
	}

}
