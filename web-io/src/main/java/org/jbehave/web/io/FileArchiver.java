package org.jbehave.web.io;

import java.io.File;

public interface FileArchiver {

	boolean isArchive(File file);

	void unarchive(File archive, File outputDir);

	File unarchivedDir(File file);	

}