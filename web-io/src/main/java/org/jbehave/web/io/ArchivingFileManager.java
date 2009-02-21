package org.jbehave.web.io;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.jbehave.web.io.ZipFileArchiver.FileUnarchiveFailedException;

public class ArchivingFileManager implements FileManager {

	private final FileArchiver archiver;
	private final File uploadDirectory;

	public ArchivingFileManager(FileArchiver archiver, File uploadDirectory) {
		this.archiver = archiver;
		this.uploadDirectory = uploadDirectory;
	}

	public List<File> list() {
		return asList(uploadDirectory().listFiles(new FileFilter(){
			public boolean accept(File file) {
				return !file.isDirectory();
			}			
		}));
	}

	private File uploadDirectory() {
		uploadDirectory.mkdirs();
		return uploadDirectory;
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
		if ( archiver.isArchive(file) ){
			// delete the unarchived directory too
			deleteFile(archiver.unarchivedDir(file));
		}
		file.delete();
	}
	
	public List<File> write(List<FileItem> fileItems, List<String> errors) {
		List<File> files = new ArrayList<File>();
		File directory = uploadDirectory();
		for (FileItem item : fileItems) {
			try {
				File file = writeItemToFile(directory, item);
				files.add(file);
				if (archiver.isArchive(file)) {
					try {
						archiver.unarchive(file, directory);
					} catch (FileUnarchiveFailedException e) {
						errors.add(e.getMessage());
					}
				}
			} catch (FileItemNameMissingException e) {
				// ignore and carry on
			} catch (FileWriteFailedException e) {
				errors.add(e.getMessage());
			}
		}
		return files;
	}

	
	private File writeItemToFile(File directory, FileItem item) {
		if (isBlank(item.getName())) {
			throw new FileItemNameMissingException(item);
		}
		File file = new File(directory, item.getName());
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