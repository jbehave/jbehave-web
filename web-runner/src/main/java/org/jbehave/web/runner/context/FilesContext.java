package org.jbehave.web.runner.context;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class FilesContext {

    public enum View {
        RELATIVE_PATH, FULL_PATH
    }

    private List<File> files = new ArrayList<File>();
    private List<String> selectedPaths = new ArrayList<String>();
    private boolean contentVisible = false;
    private Map<String, List<File>> contentFiles = new HashMap<String, List<File>>();
    private View view = View.RELATIVE_PATH;
    private List<String> errors = new ArrayList<String>();

    public FilesContext() {
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
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
            for ( File file : contentFiles.get(directoryPath) ){
                if ( isViewable(file) ){
                    list.add(new File(viewablePath(directoryPath, file)));                    
                }
            }
        }
        return list;
    }

    private String viewablePath(String directoryPath, File file) {
        return unixPath(file.getPath());
    }

    private String unixPath(String path) {
        return path.replace("\\","/");
    }

    private boolean isViewable(File file) {
       return file.getPath().matches(".*\\.[A-Za-z]+");
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


}
