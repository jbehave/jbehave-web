package org.jbehave.web.runner.wicket.pages;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.jbehave.web.io.ResourceFinder;

public class FileContent extends Template {

	public FileContent(File file) {
		String path = file.getPath();		
		String type = typeOf(path);
        add(new NoMarkupMultiLineLabel("fileContent", new ResourceFinder().resourceAsString(path), "brush: "+type));
	}

    private String typeOf(String path) {
        // add more brushes
        String ext = StringUtils.substringAfterLast(path, ".");
        if ( ext.equals("java") || ext.equals("xml") ){
            return ext;
        } else {
            return "plain";
        }
    }

}
