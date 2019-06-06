package com.pmi;

import com.beust.jcommander.JCommander;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@Slf4j
public class Server implements Runnable {
    private static int port = 8080;
    private static final String PATH_ROOT = "/ic";
    private static final String PATH_TEST = "/test";
    @Override
    public void run() {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(port);
        ServletContextHandler service = new ServletContextHandler(ServletContextHandler.SESSIONS);
        service.setContextPath(PATH_ROOT);
        try {
            service.addServlet(new ServletHolder(new IntentChainingServlet()), PATH_TEST);
        } catch (Exception e) {
            log.error("Exception when creating InstantTriggerServlet", e);
        }
        server.setHandler(service);

        try {
            server.start();
        } catch (Exception e) {
            log.error("", e);
        }
        log.info("Server Started");
        try {
            server.join();
        } catch (InterruptedException e) {
            log.error("", e);
        }
        log.info("Server End");
    }

    public static void main(String[] args) {
        Server service = new Server();
        JCommander jcommander = new JCommander(service);
        jcommander.parse(args);
        service.run();
    }
}
