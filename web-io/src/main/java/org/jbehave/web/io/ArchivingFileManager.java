package org.jbehave.web.io;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.jbehave.web.io.ZipFileArchiver.FileUnarchiveFailedException;

/**
 * File manager that uploads and unarchives data files
 */
public class ArchivingFileManager implements FileManager {

	private final FileArchiver archiver;
	private final FileMonitor monitor;
	private final File uploadDirectory;

	public ArchivingFileManager(FileArchiver archiver, FileMonitor monitor, File uploadDirectory) {
		this.archiver = archiver;
		this.monitor = monitor;
		this.uploadDirectory = uploadDirectory;
	}

	public List<File> list() {
		File directory = uploadDirectory();
		List<File> files = asList(directory.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return !file.isDirectory();
			}
		}));
		monitor.filesListed(directory, files);
		return files;
	}

	private File uploadDirectory() {
		uploadDirectory.mkdirs();
		return uploadDirectory;
	}

	public List<File> listContent(String path, boolean relativePaths) {
		File directory = archiver.directoryOf(new File(path));
		List<File> content = new ArrayList<File>();
		for (File file : archiver.listContent(directory)) {
			File contentFile = (relativePaths ? archiver.relativeTo(file,
					directory) : file);
			content.add(contentFile);
		}
		monitor.contentListed(path, directory, relativePaths, content);
		return content;
	}

	public void delete(List<String> paths) {
		for (String path : paths) {
			deleteFile(new File(path));
		}
	}

	private void deleteFile(File file) {
		if (file.isDirectory()) {
			// recursively delete children
			for (String child : file.list()) {
				deleteFile(new File(file, child));
			}
		}
		if (archiver.isArchive(file)) {
			// delete the unarchived directory too
			File directory = archiver.directoryOf(file);
			deleteFile(directory);
			monitor.fileDeleted(directory);
		}
		file.delete();
		monitor.fileDeleted(file);
	}

	public List<File> upload(List<FileItem> fileItems, List<String> errors) {
		List<File> files = new ArrayList<File>();
		File directory = uploadDirectory();
		for (FileItem item : fileItems) {
			try {
				File file = writeItemToFile(directory, item);
				files.add(file);
				monitor.fileUploaded(file);
				if (archiver.isArchive(file)) {
					try {
						archiver.unarchive(file, directory);
						monitor.fileUnarchived(file, directory);
					} catch (FileUnarchiveFailedException e) {
						errors.add(e.getMessage());
						if (e.getCause() != null) {
							errors.add(e.getCause().getMessage());
						}
					}
				}
			} catch (FileItemNameMissingException e) {
				// ignore and carry on
			} catch (FileWriteFailedException e) {
				errors.add(e.getMessage());
				if (e.getCause() != null) {
					errors.add(e.getCause().getMessage());
				}
				monitor.fileUploadFailed(item, e);
			}
		}
		return files;
	}

	private File writeItemToFile(File directory, FileItem item) {
		if (isBlank(item.getName())) {
			throw new FileItemNameMissingException(item);
		}
		File file = new File(directory, fileName(item));
		try {
			if (file.exists()) {
				file.createNewFile();
			}
			item.write(file);
		} catch (Exception e) {
			throw new FileWriteFailedException(file, e);
		}
		return file;
	}

    private String fileName(FileItem item) {
        // FileItem.getName() may return the full path, depending on the client
        // (e.g. IE or Opera)
        // FilenameUtils.getName(path) extracts file name whatever the path
        // separator (Unix or Windows)
        return FilenameUtils.getName(item.getName());
    }

	@SuppressWarnings("serial")
	public static final class FileItemNameMissingException extends
			RuntimeException {

		public FileItemNameMissingException(FileItem file) {
			super(file.toString() + " missing name");
		}

	}

	@SuppressWarnings("serial")
	public static final class FileWriteFailedException extends RuntimeException {

		public FileWriteFailedException(File file, Throwable cause) {
			super(file.toString() + " write failed", cause);
		}

	}

}