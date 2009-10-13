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
		dir = new File("/tmp", "dir");
	}

	@Test
	public void canArchiveZip() throws IOException {
		File zip = new File("target", "dir1.zip");
		dir.mkdir();
		archiver.archive(zip , dir);
	}

	@Test
	public void canUnarchiveZip() throws IOException {
		File zip = resourceFile("dir1.zip");
		assertTrue(archiver.isArchive(zip));
		dir.delete();
		dir.mkdir();
		archiver.unarchive(zip, dir);
		assertFilesUnarchived(asList("dir1", "dir1/file1.txt", "dir1/subdir1",
				"dir1/subdir1/subfile1.txt"));
	}

	@Test
	public void canListFileContentOfUnarchiveZip() throws IOException {
		File zip = resourceFile("dir1.zip");
		assertTrue(archiver.isArchive(zip));
		archiver.unarchive(zip, dir);
		List<File> content = archiver.listContent(new File(dir, "dir1"));
		assertFilesEquals(content, asList("dir1", "dir1/file1.txt",
				"dir1/subdir1", "dir1/subdir1/subfile1.txt"));
	}

	private void assertFilesEquals(List<File> content,
			List<String> expectedPaths) {
		for (int i = 0; i < content.size(); i++) {
			File file = content.get(i);
			assertEquals(file, new File(dir, expectedPaths.get(i)));
		}
	}

	private void assertFilesUnarchived(List<String> paths) {
		for (String path : paths) {
			assertFileExists(path);
		}
	}

	private void assertFileExists(String path) {
		assertTrue(new File(dir, path).exists());
	}

	private File resourceFile(String path) {
		return new File(getClass().getClassLoader().getResource(path).getFile());
	}

}