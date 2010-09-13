package org.jbehave.web.runner.context;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FilesContext {

    public enum View {
        RELATIVE_PATH, FULL_PATH
    }

    private List<File> files = new ArrayList<File>();
    private List<String> selectedPaths = new ArrayList<String>();
    private boolean contentVisible = false;
    private Map<String, List<File>> contentFiles = new HashMap<String, List<File>>();
    private List<String> errors = new ArrayList<String>();
    private View view = View.FULL_PATH;

    public FilesContext() {
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = toViewables(files);
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

    public List<String> getPaths() {
        List<String> paths = new ArrayList<String>();
        for ( File file : files ){
            paths.add(file.getPath());
        }
        return paths;
    }

    public List<String> getSelectedPaths() {
        return selectedPaths;
    }

    public void setSelectedPaths(List<String> selectedPaths) {
        this.selectedPaths = selectedPaths;
    }

    public List<File> getContentFilesAsList() {  
        List<File> list = new ArrayList<File>();
        for ( String directoryPath : contentFiles.keySet() ){
            list.addAll(toViewables(contentFiles.get(directoryPath)));
        }
        return list;
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

    public List<View> getViews() {
        return asList(View.values());
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<String> getErrors() {
        return errors;
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
