package org.jbehave.web.io;

import static org.apache.commons.lang.StringUtils.removeEnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

public class ZipFileArchiver implements FileArchiver {

	private static final String ZIP = ".zip";
	private ArchiveStreamFactory factory = new ArchiveStreamFactory();

	public boolean isArchive(File file) {
		return file.getName().endsWith(ZIP);
	}

	public File unarchivedDir(File file) {
		return new File(removeEnd(file.getPath(), ZIP));
	}

	public void unarchive(File archive, File outputDir) {
		try {
			ArchiveInputStream in = factory.createArchiveInputStream("zip",
					new FileInputStream(archive));
			ZipFile zipfile = new ZipFile(archive);
			for (Enumeration<?> e = zipfile.getEntries(); e.hasMoreElements();) {
				ZipArchiveEntry entry = (ZipArchiveEntry) e.nextElement();
				unzipEntry(entry, in, outputDir);
			}
			in.close();
		} catch (Exception e) {
			throw new FileUnarchiveFailedException(archive, outputDir, e);
		}
	}

	private void unzipEntry(ZipArchiveEntry entry, InputStream in,
			File outputDir) throws IOException {

		if (entry.isDirectory()) {
			createDir(new File(outputDir, entry.getName()));
			return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if (!outputFile.getParentFile().exists()) {
			createDir(outputFile.getParentFile());
		}
		
		copy(entry, in, outputDir);

	}

	private void createDir(File dir) throws IOException {
		if (dir.exists()){
			return;
		}
		if (!dir.mkdirs()) {
			throw new IOException("Failed to create dir " + dir);
		}
	}

	private void copy(ZipArchiveEntry entry, InputStream in, File outputDir)
			throws FileNotFoundException, IOException {
		File entryFile = new File(outputDir, entry.getName());
		OutputStream out = new FileOutputStream(entryFile);
		IOUtils.copy(in, out);
		out.close();
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
