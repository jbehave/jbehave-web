package org.jbehave.web.io;

import java.io.File;
import java.util.List;

public interface FileArchiver {

	boolean isArchive(File file);

	void unarchive(File archive, File outputDir);

	File unarchivedDir(File archive);	

	List<File> listContent(File directory);
	
}