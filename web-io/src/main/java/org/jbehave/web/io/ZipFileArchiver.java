package org.jbehave.web.io;

import static org.apache.commons.io.FilenameUtils.separatorsToUnix;
import static org.apache.commons.lang.StringUtils.remove;
import static org.apache.commons.lang.StringUtils.removeEnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.IOUtils;

/**
 * File archiver for zip files
 */
public class ZipFileArchiver implements FileArchiver {

	private static final String UNIX_SEPARATOR = "/";
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
				if (!file.isDirectory()) {
					ZipArchiveEntry entry = new ZipArchiveEntry(relativeTo(
							file, directory).getPath());
					zipEntry(entry, out, file);
				}
			}
			out.close();
		} catch (Exception e) {
			throw new FileArchiveFailedException(archive, directory, e);
		}
	}

	public File relativeTo(File file, File directory) {
		String filePath = separatorsToUnix(file.getPath());
		String directoryPath = separatorsToUnix(directory.getPath());
		if ( !directoryPath.endsWith(UNIX_SEPARATOR) ){ 
			// ensure directory has a trailing separator 
			// that will be removed from the full file path
			directoryPath = directoryPath + UNIX_SEPARATOR;
		}
		return new File(remove(filePath, directoryPath));
	}
	
	private void zipEntry(ZipArchiveEntry entry, ArchiveOutputStream out,
			File file) throws IOException, FileNotFoundException {
		out.putArchiveEntry(entry);
		IOUtils.copy(new FileInputStream(file), out);
		out.closeArchiveEntry();
	}

	public File directoryOf(File archive) {
		return new File(removeEnd(archive.getPath(), ZIP_EXT));
	}

	public void unarchive(File archive, File directory) {
		InputStream is = null;
		ArchiveInputStream in = null;
		try {
			is = new FileInputStream(archive);
			in = factory.createArchiveInputStream(ZIP, is);
			ZipArchiveEntry entry = null;
			while ((entry = (ZipArchiveEntry) in.getNextEntry()) != null) {
				unzipEntry(entry, in, directory);
			}
		} catch (Exception e) {
			throw new FileUnarchiveFailedException(archive, directory, e);
		} finally {
			close(is);
 			close(in);
		}
	}

	private void close(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<File> listContent(File file) {
		List<File> content = new ArrayList<File>();
		content.add(file);
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				content.addAll(listContent(child));
			}
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
		createDir(outputFile.getParentFile());
		copy(entry, in, outputFile);

	}

	private void createDir(File dir) throws IOException {
		if (dir.exists()) {
			return;
		}
		if (!dir.mkdirs()) {
			throw new IOException("Failed to create dir " + dir);
		}
	}

	private void copy(ZipArchiveEntry entry, InputStream in, File outputFile)
			throws FileNotFoundException, IOException {
		OutputStream out = new FileOutputStream(outputFile);
		try {
			IOUtils.copy(in, out);
		} finally {
			out.close();
		}
	}

	@SuppressWarnings("serial")
	public static final class FileArchiveFailedException extends
			RuntimeException {

		public FileArchiveFailedException(File archive, File directory,
				Exception cause) {
			super("Failed to archive dir " + directory + " to " + archive,
					cause);
		}

	}

	@SuppressWarnings("serial")
	public static final class FileUnarchiveFailedException extends
			RuntimeException {

		public FileUnarchiveFailedException(File archive, File directory,
				Exception cause) {
			super("Failed to unarchive " + archive + " to dir " + directory,
					cause);
		}

	}

}
