package org.jbehave.web.queue;

import java.io.File;

public class WebQueueConfiguration {

    private File navigatorDirectory = new File("src/main/storynavigator");
    private String navigatorPage = "navigator.html";
    private int port = 8089;
    private String welcomeFile = "run-story.html";

    public File navigatorDirectory() {
        return navigatorDirectory;
    }
    
    public String navigatorPage() {
        return navigatorPage;
    }

    public int port() {
        return port;
    }

    public String welcomeFile() {
        return welcomeFile;
    }

    public WebQueueConfiguration useNavigatorDirectory(File navigatorDir) {
        this.navigatorDirectory = navigatorDir;
        return this;
    }

    public WebQueueConfiguration useNavigatorPage(String navigatorPage){
        this.navigatorPage = navigatorPage;
        return this;
    }

    public WebQueueConfiguration usePort(int port) {
        this.port = port;
        return this;
    }

    public WebQueueConfiguration useWelcomeFile(String welcomeFile) {
        this.welcomeFile = welcomeFile;
        return this;
    }

}
