package com.dmgburg.dozor.engine

import groovy.transform.CompileStatic
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.ContextHandler
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.springframework.core.io.ClassPathResource
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

@CompileStatic
public class TestJettyServer {
    Server server
    int port
    String contextPath
    String configLocation
    String mappingUrl
    CountDownLatch latch

    TestJettyServer(int port = 8080,
                String contextPath= "/",
                String configLocation = "com.dmgburg.dozor.configs",
                String mappingUrl = "/",
                CountDownLatch latch) {
        this.port = port
        this.contextPath = contextPath
        this.configLocation = configLocation
        this.mappingUrl = mappingUrl
        this.latch = latch
    }

    public static void main(String[] args) {
        new TestJettyServer(new CountDownLatch(1)).start()
    }

    public void start() throws Exception
    {
        server = new Server( port );

        ContextHandler context = new ContextHandler();
        context.setContextPath( "/" );
        context.setHandler( getServletContextHandler(getContext()) );

        server.setHandler( getServletContextHandler(getContext()) );

        server.start();
        latch.countDown()
    }

    public void stop(){
        server.stop()
    }

    private ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(contextPath);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), mappingUrl);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("html").getURI().toString());
        return contextHandler;
    }

    private WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(configLocation);
        return context;
    }
}