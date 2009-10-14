package org.jbehave.web.io;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ZipFileArchiverTest {

	private File dir;
	private FileArchiver archiver = new ZipFileArchiver();

	@Before
	public void setup() throws IOException {
		dir = new File("target", "dir");
		dir.createNewFile();
	}

	@Test
	public void canArchiveZip() throws IOException {
		File zip = createFile("archive.zip");
		archiver.archive(zip, dir);
	}

	private File createFile(String path) throws IOException {
		File parent = new File("target");
		parent.mkdirs();
		File file = new File(parent, path);
		file.createNewFile();
		return file;
	}

	@Test
	public void canUnarchiveZip() throws IOException {
		File zip = resourceFile("archive.zip");
		assertTrue(archiver.isArchive(zip));
		clearDir(dir);
		archiver.unarchive(zip, dir);
		assertFilesUnarchived(asList("archive", "archive/file1.txt", "archive/subdir1",
				"archive/subdir1/subfile1.txt"));
	}

	private void clearDir(File dir) {
		dir.delete();
		dir.mkdir();
	}

	@Test
	public void canListFileContentOfUnarchiveZip() throws IOException {
		File zip = resourceFile("archive.zip");
		assertTrue(archiver.isArchive(zip));
		archiver.unarchive(zip, dir);
		List<File> content = archiver.listContent(new File(dir, "archive"));
		assertFilesEquals(content, asList("archive", "archive/file1.txt",
				"archive/subdir1", "archive/subdir1/subfile1.txt"));
	}

	private void assertFilesEquals(List<File> content,
			List<String> expectedPaths) {
		for (int i = 0; i < content.size(); i++) {
			File file = content.get(i);
			assertEquals(file, new File(dir, expectedPaths.get(i)));
		}
	}

	private void assertFilesUnarchived(List<String> paths) {
		ResourceFinder finder = new ResourceFinder("");
		for (String path : paths) {
			File file = new File(dir, path);
			assertTrue(file.exists());
			if ( !file.isDirectory() ){
				assertTrue(finder.resourceAsString(file.getPath()).length() > 0);
			}
		}
	}

	private File resourceFile(String path) {
		return new File(getClass().getClassLoader().getResource(path).getFile());
	}

}