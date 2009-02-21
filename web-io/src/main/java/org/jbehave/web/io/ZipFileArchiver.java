package org.jbehave.web.io;

import static org.apache.commons.lang.StringUtils.removeEnd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

// unarchive() impl adapted from http://piotrga.wordpress.com/2008/05/07/how-to-unzip-archive-in-java/
// works as tested, but should be replaced with commons-compress (http://commons.apache.org/sandbox/compress) when released
public class ZipFileArchiver implements FileArchiver {

	private static final String ZIP = ".zip";

	public boolean isArchive(File file) {
		return file.getName().endsWith(ZIP);
	}

	public File unarchivedDir(File file) {
		return new File(removeEnd(file.getPath(), ZIP));
	}

	public void unarchive(File archive, File outputDir) {
		try {
			ZipFile zipfile = new ZipFile(archive);
			for (Enumeration<?> e = zipfile.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				unzipEntry(zipfile, entry, outputDir);
			}
		} catch (Exception e) {
			throw new FileUnarchiveFailedException(archive, outputDir, e);
		}
	}

	private void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir)
			throws IOException {

		if (entry.isDirectory()) {
			createDir(new File(outputDir, entry.getName()));
			return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if (!outputFile.getParentFile().exists()) {
			createDir(outputFile.getParentFile());
		}

		// Copy entry to output
		InputStream in = zipfile.getInputStream(entry);
		copy(in, outputFile);
	}

	private void copy(InputStream inputStream, File outputFile)
			throws IOException {
		InputStream in = new BufferedInputStream(inputStream);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(
				outputFile));

		try {
			IOUtils.copy(in, out);
		} finally {
			out.close();
			in.close();
		}
	}

	private void createDir(File dir) throws IOException {
		if (!dir.mkdirs()) {
			throw new IOException("Failed to create dir " + dir);
		}
	}

	@SuppressWarnings("serial")
	public static final class FileUnarchiveFailedException extends
			RuntimeException {

		public FileUnarchiveFailedException(File archive, File outputDir,
				Exception cause) {
			super(archive.toString() + File.pathSeparator
					+ outputDir.toString(), cause);
		}

	}

}
