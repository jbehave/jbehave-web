package org.jbehave.web.runner.wicket.pages;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.jbehave.web.io.ResourceFinder;

public class ViewFileContent extends Template {

	public ViewFileContent(File file) {
		String path = file.getPath();		
		String type = typeOf(path);
        add(new SyntaxHighlighterLabel("fileContent", new ResourceFinder().resourceAsString(path), "brush: "+type));
	}

    private String typeOf(String path) {
        String ext = StringUtils.substringAfterLast(path, ".");
        if ( ext.equals("txt") ){
            return "plain";
        } else {
            return ext;
        }
    }

}
