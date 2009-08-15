package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.codehaus.waffle.menu.Menu;
import org.jbehave.web.io.FileManager;
import org.jbehave.web.runner.waffle.controllers.FilesController;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class FilesControllerTest {

	private Mockery mockery = new Mockery();
	private static final Menu MENU = new Menu();
	private FileManager manager = mockery.mock(FileManager.class);

	@Test
	public void canListFiles() {
		final List<File> files = asList(new File("file1"));
		mockery.checking(new Expectations() {
			{
				one(manager).list();
				will(returnValue(files));
			}
		});
		FilesController controller = new FilesController(MENU, manager);
		controller.list();
		assertEquals(files, controller.getFiles());
	}

	@Test
	public void canDeleteFiles() {
		final List<File> files = asList();
		final List<String> paths = asList("file1");
		mockery.checking(new Expectations() {
			{
				one(manager).delete(paths);
				one(manager).list();
				will(returnValue(files));
			}
		});
		FilesController controller = new FilesController(MENU, manager);
		controller.setSelectedPaths(paths);
		controller.delete();
		assertEquals(files, controller.getFiles());
	}

}
