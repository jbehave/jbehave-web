package org.jbehave.web.runner.waffle.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.web.io.FileManager;

public class FilesController extends MenuAwareController {

	private final FileManager manager;
	private List<File> files = new ArrayList<File>();
	private List<String> selectedPaths = new ArrayList<String>();
	private Map<String,List<File>> contentFiles = new HashMap<String,List<File>>();
	
	public FilesController(Menu menu, FileManager manager) {
		super(menu);
		this.manager = manager;
	}

	@ActionMethod(asDefault = true)
	public void list() {
		this.files = manager.list();
	}

	@ActionMethod
	public void showContent() {
		contentFiles.clear();
		for (String path : selectedPaths) {
			contentFiles.put(path, manager.listContent(path));			
		}
	}

	@ActionMethod
	public void hideContent() {
		contentFiles.clear();
	}

	@ActionMethod
	public void delete() {
		manager.delete(selectedPaths);
		list();
	}
	
	public List<File> getFiles() {
		return files;
	}
	
	public Map<String, List<File>> getContentFiles() {
		return contentFiles;
	}

	public List<String> getSelectedPaths() {
		return selectedPaths;
	}

	public void setSelectedPaths(List<String> selectedPaths) {
		this.selectedPaths = selectedPaths;
	}

}
