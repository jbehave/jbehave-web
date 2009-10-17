package org.jbehave.web.io;

import java.io.File;
import java.util.List;

public interface FileMonitor {
	
	void contentListed(String path, File directory,
			boolean relativePaths, List<File> content);

	void filesListed(File uploadDirectory, List<File> files);

	void fileUnarchived(File file, File directory);

	void fileDeleted(File file);

}
