package org.jbehave.web.runner.waffle.controllers;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.io.FileUploader;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.web.io.FileManager;

public class FileUploadController extends MenuAwareController {

	private final FileUploader uploader;
	private final FileManager manager;
	private List<String> errors = new ArrayList<String>();
	private List<File> uploadedFiles = new ArrayList<File>();
	private int filesToUpload = 1;

	public FileUploadController(Menu menu, FileUploader uploader, FileManager manager) {
		super(menu);
		this.uploader = uploader;
		this.manager = manager;
	}

	@ActionMethod(asDefault = true)
	@PRG(false)
	public void upload() {
		errors.clear();
		uploadedFiles.clear();
		List<FileItem> files = uploader.getFiles();
		errors.addAll(uploader.getErrors());
		uploadedFiles.addAll(manager.upload(files, errors));
	}

	public Collection<String> getErrors() {
		return errors;
	}

	public List<File> getUploadedFiles() {
		return uploadedFiles;
	}

	public int getFilesToUpload() {
		return filesToUpload;
	}

}
