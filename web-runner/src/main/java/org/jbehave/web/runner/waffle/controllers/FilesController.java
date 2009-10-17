package org.jbehave.web.runner.waffle.controllers;

import static org.jbehave.web.runner.waffle.controllers.FilesContext.View.RELATIVE_PATH;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.web.io.FileManager;

public class FilesController extends MenuAwareController {

	private final FileManager manager;
	private FilesContext filesContext;

	public FilesController(Menu menu, FileManager manager) {
		super(menu);
		this.manager = manager;
		this.filesContext = new FilesContext();
	}

	@ActionMethod(asDefault = true)
	public void list() {
		this.filesContext.setFiles(manager.list());
	}

	@ActionMethod
	public void showContent() {
		Map<String, List<File>> contentFiles = filesContext.getContentFiles();
		contentFiles.clear();
		boolean relativePaths = filesContext.getView() == RELATIVE_PATH ? true
				: false;
		for (String path : filesContext.getSelectedPaths()) {
			List<File> content = manager.listContent(path, relativePaths);
			if (content.size() > 0) {
				contentFiles.put(content.get(0).getPath(), content);
			}
		}
	}

	@ActionMethod
	public void hideContent() {
		filesContext.getContentFiles().clear();
	}

	@ActionMethod
	public void delete() {
		manager.delete(filesContext.getSelectedPaths());
		hideContent();
		list();
	}

	public FilesContext getFilesContext() {
		return filesContext;
	}

	public void setFilesContext(FilesContext filesContext) {
		this.filesContext = filesContext;
	}

}
