package org.jbehave.web.io;

import static org.apache.commons.lang.StringUtils.removeEnd;
import static org.apache.commons.lang.StringUtils.removeStart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

/**
 * File archiver for zip files
 */
public class ZipFileArchiver implements FileArchiver {

	private static final String ZIP = "zip";
	private static final String ZIP_EXT = ".zip";
	private ArchiveStreamFactory factory = new ArchiveStreamFactory();

	public boolean isArchive(File file) {
		return file.getName().endsWith(ZIP_EXT);
	}

	public void archive(File archive, File directory) {
		try {
			ArchiveOutputStream out = factory.createArchiveOutputStream(ZIP,
					new FileOutputStream(archive));
			List<File> files = listContent(directory);
			for (File file : files) {
				if ( !file.isDirectory() ){
					ZipArchiveEntry entry = new ZipArchiveEntry(relativeTo(file, directory).getPath());
					zipEntry(entry, out, file);					
				}
			}
			out.close();
		} catch (Exception e) {
			throw new FileArchiveFailedException(archive, directory, e);
		}
	}

	public File relativeTo(File file, File directory) {
		return new File(removeStart(file.getPath(), directory.getPath()+"/"));
	}

	private void zipEntry(ZipArchiveEntry entry, ArchiveOutputStream out, File file)
			throws IOException, FileNotFoundException {
		out.putArchiveEntry(entry);
		IOUtils.copy(new FileInputStream(file), out);
		out.closeArchiveEntry();
	}

	public File directoryOf(File archive) {
		return new File(removeEnd(archive.getPath(), ZIP_EXT));
	}

	public void unarchive(File archive, File directory) {
		try {
			ArchiveInputStream in = factory.createArchiveInputStream(ZIP,
					new FileInputStream(archive));
			ZipFile zipfile = new ZipFile(archive);
			for (Enumeration<?> e = zipfile.getEntries(); e.hasMoreElements();) {
				ZipArchiveEntry entry = (ZipArchiveEntry) e.nextElement();
				unzipEntry(entry, in, directory);
			}
			in.close();
		} catch (Exception e) {
			throw new FileUnarchiveFailedException(archive, directory, e);
		}
	}

	public List<File> listContent(File directory) {
		List<File> content = new ArrayList<File>();
		try {
			content.add(directory);
			if (directory.isDirectory()) {
				for (File file : directory.listFiles()) {
					content.addAll(listContent(file));
				}
			}
		} catch (Exception e) {
		}
		return content;
	}

	private void unzipEntry(ZipArchiveEntry entry, InputStream in,
			File directory) throws IOException {

		if (entry.isDirectory()) {
			createDir(new File(directory, entry.getName()));
			return;
		}

		File outputFile = new File(directory, entry.getName());
		if (!outputFile.getParentFile().exists()) {
			createDir(outputFile.getParentFile());
		}

		copy(entry, in, directory);

	}

	private void createDir(File dir) throws IOException {
		if (dir.exists()) {
			return;
		}
		if (!dir.mkdirs()) {
			throw new IOException("Failed to create dir " + dir);
		}
	}

	private void copy(ZipArchiveEntry entry, InputStream in, File directory)
			throws FileNotFoundException, IOException {
		File entryFile = new File(directory, entry.getName());
		OutputStream out = new FileOutputStream(entryFile);
		IOUtils.copy(in, out);
		out.close();
	}
	
	@SuppressWarnings("serial")
	public static final class FileArchiveFailedException extends
			RuntimeException {

		public FileArchiveFailedException(File archive, File directory,
				Exception cause) {
			super("Failed to archive dir " + directory + " to " + archive, cause);
		}

	}


	@SuppressWarnings("serial")
	public static final class FileUnarchiveFailedException extends
			RuntimeException {

		public FileUnarchiveFailedException(File archive, File directory,
				Exception cause) {
			super("Failed to unarchive " + archive + " to dir " + directory, cause);
		}

	}

}
