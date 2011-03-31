package org.jbehave.web.queue;

import java.io.File;

public class WebQueueConfiguration {

    private File navigatorDir;
    private int port = 8089;
    private String welcomeFile = "run-story.html";

    public File navigatorDirectory() {
        return navigatorDir;
    }

    public int port() {
        return port;
    }

    public String welcomeFile() {
        return welcomeFile;
    }

    public WebQueueConfiguration useNavigatorDirectory(File navigatorDir) {
        this.navigatorDir = navigatorDir;
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
