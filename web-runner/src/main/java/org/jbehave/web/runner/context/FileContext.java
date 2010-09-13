package org.jbehave.web.runner.context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FileContext {

    private List<File> files = new ArrayList<File>();
    private Map<String, List<File>> contentFiles = new HashMap<String, List<File>>();
    private boolean contentVisible = false;
    private List<String> errors = new ArrayList<String>();

    public FileContext() {
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = toViewables(files);
    }
    
    public List<File> getContentFilesAsList() {  
        List<File> list = new ArrayList<File>();
        for ( String directoryPath : contentFiles.keySet() ){
            list.addAll(toViewables(contentFiles.get(directoryPath)));
        }
        return list;
    }

    private List<File> toViewables(List<File> files) {
        List<File> viewableFiles = new ArrayList<File>();
        for (File file : files) {
            ViewableFile viewableFile = new ViewableFile(file);
            if ( viewableFile.isViewable() ){
                viewableFiles.add(viewableFile);
            }
        }
        return viewableFiles;
    }

    public boolean getContentVisible() {
        return contentVisible;
    }

    public void setContentVisible(boolean contentVisible) {
        this.contentVisible = contentVisible;
    }
    
    public Map<String, List<File>> getContentFiles() {
        return contentFiles;
    }

    public void setContentFiles(Map<String, List<File>> contentFiles) {
        this.contentFiles = contentFiles;
    }

    public List<String> getErrors() {
        return errors;
    }
    
    public void setErrors(List<String> errors){
        this.errors = errors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @SuppressWarnings("serial")
    public static class ViewableFile extends File {

        public ViewableFile(File file) {
            super(file.getPath());
        }
        
        public String getPath(){
            return unixPath(super.getPath());
        }
        
        private String unixPath(String path) {
            return path.replace("\\","/");
        }
        
        public boolean isViewable() {
            return getPath().matches(".*\\.[A-Za-z]+");
        }

    }


}
