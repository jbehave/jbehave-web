package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.waffle.io.FileUploader;
import org.codehaus.waffle.menu.Menu;
import org.jbehave.web.io.FileManager;
import org.junit.Test;

public class FileUploadControllerTest {

	private static final Menu MENU = new Menu();
	private FileManager manager = mock(FileManager.class);
	private FileUploader uploader = mock(FileUploader.class);
	private FileItem fileItem1 = mock(FileItem.class);

	@Test
	public void canUploadFiles() {
		List<FileItem> fileItems = asList(fileItem1);
		List<File> files = asList(new File("file1"));
		List<String> errors = new ArrayList<String>();
		when(uploader.getFiles()).thenReturn(fileItems);
        when(uploader.getErrors()).thenReturn(errors);
        when(manager.upload(fileItems, errors)).thenReturn(files);
		FileUploadController controller = new FileUploadController(MENU, uploader, manager);
		controller.upload();
		assertEquals(files, controller.getUploadedFiles());
	}

}
