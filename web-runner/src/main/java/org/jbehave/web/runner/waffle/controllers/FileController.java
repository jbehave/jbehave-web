package org.jbehave.web.runner.waffle.controllers;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.web.io.ResourceFinder;

public class FileController extends MenuAwareController {

	private ResourceFinder resourceFinder;
	private String selectedPath = "";
	private String fileContent;
	
	public FileController(Menu menu) {
		super(menu);
		this.resourceFinder = new ResourceFinder("");
	}

	@ActionMethod(asDefault=true)
	public void viewContent() {
		this.fileContent = resourceFinder.resourceAsString(selectedPath);
	}

	public String getSelectedPath() {
		return selectedPath;
	}

	public void setSelectedPath(String selectedPath) {
		this.selectedPath = selectedPath;
	}
	
	public String getFileContent(){
		return fileContent;
	}
}
