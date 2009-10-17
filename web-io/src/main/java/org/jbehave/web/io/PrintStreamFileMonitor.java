package org.jbehave.web.io;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

public class PrintStreamFileMonitor implements FileMonitor {

	private final PrintStream output;

	public PrintStreamFileMonitor() {
		this(System.out);
	}

	public PrintStreamFileMonitor(PrintStream output) {
		this.output = output;
	}

	protected void print(PrintStream output, String message) {
		output.println(message);
	}

	public void contentListed(String path, File directory,
			boolean relativePaths, List<File> content) {
		print(output, "Listed content of path " + path + " from directory "
				+ directory + " using " + (relativePaths ? "relative" : "full")
				+ " paths: " + content);
	}

	public void fileDeleted(File file) {
		print(output, "Deleted file " + file);
	}

	public void fileUnarchived(File file, File directory) {
		print(output, "Unarchived file " + file + " to directory " + directory);
	}

	public void filesListed(File uploadDirectory, List<File> files) {
		print(output, "Listed files from upload directory " + uploadDirectory
				+ ": " + files);
	}

}
