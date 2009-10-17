package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FilesContext {

	public enum View {
		RELATIVE_PATH, FULL_PATH
	}

	private List<File> files = new ArrayList<File>();
	private List<String> selectedPaths = new ArrayList<String>();
	private Map<String, List<File>> contentFiles = new HashMap<String, List<File>>();
	private View view = View.RELATIVE_PATH;

	public FilesContext() {
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<String> getSelectedPaths() {
		return selectedPaths;
	}

	public void setSelectedPaths(List<String> selectedPaths) {
		this.selectedPaths = selectedPaths;
	}

	public Map<String, List<File>> getContentFiles() {
		return contentFiles;
	}

	public void setContentFiles(Map<String, List<File>> contentFiles) {
		this.contentFiles = contentFiles;
	}

	public List<View> getViews() {
		return asList(View.values());
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
