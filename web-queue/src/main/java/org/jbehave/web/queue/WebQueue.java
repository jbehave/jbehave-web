package org.jbehave.web.queue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.failures.BatchFailures;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporterBuilder;

public class WebQueue {

    private final Embedder embedder;
    private final BatchFailures batchFailures;
    private final List<Future<Embedder.ThrowableStory>> futures;
    private final WebQueueConfiguration configuration;
    private Server server;

    public WebQueue(Embedder embedder, BatchFailures batchFailures, List<Future<Embedder.ThrowableStory>> futures, WebQueueConfiguration configuration) {
        this.embedder = embedder;
        this.batchFailures = batchFailures;
        this.futures = futures;
        this.configuration = configuration;
    }
    
    @SuppressWarnings("serial")
    public void start() {
        this.server = new Server(configuration.port());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new HttpServlet() {
            protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                    IOException {
                String job = "" + System.currentTimeMillis();
                String storyInput = request.getParameter("story");
                Story story = embedder.configuration().storyParser().parseStory(storyInput, job);

                embedder.enqueueStory(batchFailures, MetaFilter.EMPTY, futures, job, story);
                response.setContentType("text/html");
                ;
                response.sendRedirect("/" + configuration.navigatorPage() + "?job=" + job);
            }
        }), "*.enqueue");

        ResourceHandler viewHandler = new ResourceHandler();
        viewHandler.setDirectoriesListed(true);
        viewHandler.setWelcomeFiles(new String[] { configuration.welcomeFile() });

        try {
            StoryReporterBuilder builder = embedder.configuration().storyReporterBuilder();
            String viewDir = builder.outputDirectory().getPath() + "/" + builder.viewResources().getProperty("view");
            viewHandler.setResourceBase(configuration.navigatorDirectory().getCanonicalPath() + viewDir);
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] { context, viewHandler, new DefaultHandler() });
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
