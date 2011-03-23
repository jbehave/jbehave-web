package org.jbehave.web.listener;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.Story;
import org.jbehave.core.steps.CandidateSteps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

public class JBehaveWebQueue {

    private EmbedderControls embedderControls;
    private Configuration configuration;
    private List<CandidateSteps> candidateSteps;
    private BatchFailures batchFailures;
    private List<Future<Throwable>> futures;
    private Embedder embedder;
    private final File projectDir;

    public JBehaveWebQueue(EmbedderControls embedderControls, Configuration configuration, List<CandidateSteps> candidateSteps,
                           BatchFailures batchFailures, List<Future<Throwable>> futures, Embedder embedder, File projectDir) {
        this.embedderControls = embedderControls;
        this.configuration = configuration;
        this.candidateSteps = candidateSteps;
        this.batchFailures = batchFailures;
        this.futures = futures;
        this.embedder = embedder;
        this.projectDir = projectDir;
    }

    private Server server = new Server(8089);

    public void start() {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HttpServlet() {
            protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String contrivedPath = "" + System.currentTimeMillis();
                String storyInput = request.getParameter("story");
                System.out.println("--> story: " + storyInput);
                Story story = configuration.storyParser().parseStory(storyInput, contrivedPath);

                embedder.enqueueStory(embedderControls, configuration, candidateSteps, batchFailures,
                        MetaFilter.EMPTY, futures, contrivedPath, story);
                response.setContentType("text/html");
                response.sendRedirect("/navigator.html?job=" + contrivedPath);
            }
        }),"*.enqueue");

        ResourceHandler jbehaveTargetViewDir = new ResourceHandler();
        jbehaveTargetViewDir.setDirectoriesListed(true);
        jbehaveTargetViewDir.setWelcomeFiles(new String[]{"run-story.html"});

        try {
            jbehaveTargetViewDir.setResourceBase(projectDir.getCanonicalPath() + "/target/jbehave/view");
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] { context, jbehaveTargetViewDir, new DefaultHandler() });
            server.setHandler(handlers);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
