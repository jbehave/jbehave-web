package org.jbehave.web.queue;

import java.io.IOException;

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

public class WebQueue {

    private final Embedder embedder;
    private final WebQueueConfiguration configuration;
    private Server server;

    public WebQueue(Embedder embedder, WebQueueConfiguration configuration) {
        this.embedder = embedder;
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
                String storyInput = request.getParameter("story");
                String storyId = "" + System.currentTimeMillis();
                embedder.enqueueStoryAsText(storyInput, storyId);
                response.setContentType("text/html");
                response.sendRedirect("/" + configuration.navigatorPage() + "?job=" + storyId);
            }
        }), "*.enqueue");

        ResourceHandler viewHandler = new ResourceHandler();
        viewHandler.setDirectoriesListed(true);
        viewHandler.setWelcomeFiles(new String[] { configuration.welcomeFile() });

        try {
            viewHandler.setResourceBase(configuration.navigatorDirectory().getCanonicalPath());
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
