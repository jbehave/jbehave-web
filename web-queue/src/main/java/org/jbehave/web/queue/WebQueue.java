package org.jbehave.web.queue;

import java.io.File;
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

public class WebQueue {

    private final Embedder embedder;
    private final BatchFailures batchFailures;
    private final List<Future<Embedder.ThrowableStory>> futures;
    private final File navigatorDir;
    
    private Server server = new Server(8089);

    public WebQueue(Embedder embedder, BatchFailures batchFailures, List<Future<Embedder.ThrowableStory>> futures, File navigatorDir) {
        this.embedder = embedder;
        this.batchFailures = batchFailures;
        this.futures = futures;
        this.navigatorDir = navigatorDir;
    }

    public void start() {

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
                response.sendRedirect("/navigator.html?job=" + job);
            }
        }), "*.enqueue");

        ResourceHandler viewDir = new ResourceHandler();
        viewDir.setDirectoriesListed(true);
        viewDir.setWelcomeFiles(new String[] { "run-story.html" });

        try {
            viewDir.setResourceBase(navigatorDir.getCanonicalPath() + "/target/jbehave/view");
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] { context, viewDir, new DefaultHandler() });
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
