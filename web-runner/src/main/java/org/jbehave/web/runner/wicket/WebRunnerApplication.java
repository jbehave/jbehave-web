package org.jbehave.web.runner.wicket;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.Steps;
import org.jbehave.web.io.ArchivingFileManager;
import org.jbehave.web.io.FileManager;
import org.jbehave.web.io.FileMonitor;
import org.jbehave.web.io.SilentFileMonitor;
import org.jbehave.web.io.ZipFileArchiver;
import org.jbehave.web.runner.wicket.pages.DataFiles;
import org.jbehave.web.runner.wicket.pages.FindSteps;
import org.jbehave.web.runner.wicket.pages.Home;
import org.jbehave.web.runner.wicket.pages.RunStory;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;

public class WebRunnerApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        addComponentInstantiationListener(new GuiceComponentInjector(this, modules()));
        mountBookmarkablePage("/home", Home.class);
        mountBookmarkablePage("/data/files", DataFiles.class);
        mountBookmarkablePage("/steps/find", FindSteps.class);
        mountBookmarkablePage("/story/run", RunStory.class);
    }

    private Module[] modules() {
        return new Module[]{new ApplicationModule()};
    }
    
    protected class ApplicationModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(Configuration.class).toInstance(configuration());
            bind(StoryRunner.class).toInstance(storyRunner());
            bind(new TypeLiteral<List<CandidateSteps>>(){}).toInstance(candidateSteps());
            bind(FileManager.class).toInstance(fileManager());
        }

    }

    protected Configuration configuration() {
        return new MostUsefulConfiguration();
    }

    protected StoryRunner storyRunner() {
        return new StoryRunner();
    }

    protected List<CandidateSteps> candidateSteps() {
        return Arrays.asList((CandidateSteps)new Steps());
    }

    protected FileMonitor fileMonitor() {
        return new SilentFileMonitor();
    }

    protected File uploadDirectory() {
        return new File("/tmp", "upload");
    }

    protected ZipFileArchiver fileArchiver() {
        return new ZipFileArchiver();
    }

    protected FileManager fileManager() {
        return new ArchivingFileManager(fileArchiver(), fileMonitor(), uploadDirectory());
    }

    public Class<Home> getHomePage() {
        return Home.class;
    }

}
