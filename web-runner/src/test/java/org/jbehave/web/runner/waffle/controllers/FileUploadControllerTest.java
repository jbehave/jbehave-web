package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.waffle.io.FileUploader;
import org.codehaus.waffle.menu.Menu;
import org.jbehave.web.io.FileManager;
import org.jbehave.web.runner.waffle.controllers.FileUploadController;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class FileUploadControllerTest {

	private Mockery mockery = new Mockery();
	private static final Menu MENU = new Menu();
	private FileManager manager = mockery.mock(FileManager.class);
	private FileUploader uploader = mockery.mock(FileUploader.class);
	private FileItem fileItem1 = mockery.mock(FileItem.class);

	@Test
	public void canUploadFiles() {
		final List<FileItem> fileItems = asList(fileItem1);
		final List<File> files = asList(new File("file1"));
		final List<String> errors = new ArrayList<String>();
		mockery.checking(new Expectations() {			
			{
				one(uploader).getFiles();
				will(returnValue(fileItems));
				one(uploader).getErrors();
				will(returnValue(errors));
				one(manager).upload(with(equal(fileItems)), with(equal(errors)));
				will(returnValue(files));
			}
		});
		FileUploadController controller = new FileUploadController(MENU, uploader, manager);
		controller.upload();
		assertEquals(files, controller.getUploadedFiles());
	}

}
