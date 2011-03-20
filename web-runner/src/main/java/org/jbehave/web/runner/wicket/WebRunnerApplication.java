package org.jbehave.web.runner.wicket;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.inject.Inject;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.EmbedderMonitor;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.Story;
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
        mountBookmarkablePage("/steps/find", FindSteps.class);
        mountBookmarkablePage("/story/run", RunStory.class);
        mountBookmarkablePage("/data/files", DataFiles.class);
    }

    private Module[] modules() {
        return new Module[]{new ApplicationModule()};
    }
    
    protected class ApplicationModule extends AbstractModule {

        @Override
        protected void configure() {

            EmbedderControls embedderControls = new EmbedderControls();

            bind(Configuration.class).toInstance(configuration());
            bind(StoryRunner.class).toInstance(storyRunner());
            bind(new TypeLiteral<List<CandidateSteps>>(){}).toInstance(candidateSteps());
            bind(FileManager.class).toInstance(fileManager());
            bind(EmbedderControls.class).toInstance(embedderControls);
            bind(ExecutorService.class).toInstance(createExecutorService(embedderControls));
            bind(new TypeLiteral<List<Future<Throwable>>>(){}).toInstance(new ArrayList<Future<Throwable>>());
            bind(Embedder.class).toInstance(new Embedder() {

                @Override
                protected EnqueuedStory makeEnqueuedStory(EmbedderControls embedderControls, Configuration configuration,
                                                          List<CandidateSteps> candidateSteps, BatchFailures batchFailures,
                                                          MetaFilter filter, String storyPath, Story story,
                                                          EmbedderMonitor embedderMonitor, StoryRunner storyRunner) {
                    // want to do something thread-safe with story-reporter here.

                    return new MyEnqueuedStory(storyPath, configuration, candidateSteps, story, filter, embedderControls,
                            batchFailures, embedderMonitor, storyRunner);
                }

            });
        }

    }

    /**
     * Creates a {@link java.util.concurrent.ThreadPoolExecutor} using the number of threads defined
     * in the {@link org.jbehave.core.embedder.EmbedderControls#threads()}
     *
     * @return An ExecutorService
     */
    protected ExecutorService createExecutorService(EmbedderControls embedderControls) {
        int threads = embedderControls.threads();
        if (threads == 1) {
            // this is necessary for situations where people use the PerStoriesWebDriverSteps class.
            return new Embedder.NonThreadingExecutorService();
        } else {
            return Executors.newFixedThreadPool(threads);
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

    private static class MyEnqueuedStory extends Embedder.EnqueuedStory {
        private MyEnqueuedStory(String storyPath, Configuration configuration, List<CandidateSteps> candidateSteps,
                                Story story, MetaFilter filter, EmbedderControls embedderControls, BatchFailures batchFailures,
                                EmbedderMonitor embedderMonitor, StoryRunner storyRunner) {
            super(storyPath, configuration, candidateSteps, story, filter, embedderControls,
                    batchFailures, embedderMonitor, storyRunner);
        }
    }

}
