/**
 * 
 */
package org.jbehave.web.io;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

/**
 * Allow to list, delete and upload data files.
 */
public interface FileManager {

	List<File> list();
	
	List<File> listContent(String path);

	void delete(List<String> paths);

	List<File> upload(List<FileItem> fileItems, List<String> errors);

}