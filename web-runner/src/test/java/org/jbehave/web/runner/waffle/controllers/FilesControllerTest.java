package org.jbehave.web.runner.waffle.controllers;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.codehaus.waffle.menu.Menu;
import org.jbehave.web.io.FileManager;
import org.junit.Test;

public class FilesControllerTest {

    private static final Menu MENU = new Menu();
    private FileManager manager = mock(FileManager.class);

    @Test
    public void canListFiles() {
        final List<File> files = asList(new File("archive1"));
        when(manager.list()).thenReturn(files);
        FilesController controller = new FilesController(MENU, manager);
        controller.list();
        assertEquals(files, controller.getFilesContext().getFiles());
    }

    @Test
    public void canListContentFiles() {
        final List<String> paths = asList("archive1.zip");
        final List<File> files = asList(new File("archive1"), new File("file1"), new File("file2"));
        final boolean relativeContentPaths = true;
        for (String path : paths) {
            when(manager.listContent(path, relativeContentPaths)).thenReturn(files);
        }
        FilesController controller = new FilesController(MENU, manager);
        FilesContext filesContext = controller.getFilesContext();
        filesContext.setSelectedPaths(paths);
        controller.showContent();
        assertEquals(files, filesContext.getContentFiles().get("archive1"));
    }

    @Test
    public void canHideContentFiles() {
        FilesController controller = new FilesController(MENU, manager);
        controller.hideContent();
        assertTrue(controller.getFilesContext().getContentFiles().isEmpty());
    }

    @Test
    public void canDeleteFiles() {
        final List<File> files = asList();
        final List<String> paths = asList("file1");
        when(manager.list()).thenReturn(files);
        FilesController controller = new FilesController(MENU, manager);
        FilesContext filesContext = controller.getFilesContext();
        filesContext.setSelectedPaths(paths);
        controller.delete();
        verify(manager).delete(paths);
        assertEquals(files, filesContext.getFiles());
    }

}
