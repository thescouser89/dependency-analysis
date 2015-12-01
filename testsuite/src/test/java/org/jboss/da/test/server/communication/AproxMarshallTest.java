package org.jboss.da.test.server.communication;

import org.jboss.da.common.util.Configuration;
import org.jboss.da.common.util.ConfigurationParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.inject.Inject;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.PathTemplateHandler;
import io.undertow.util.Headers;

public class AproxMarshallTest {

    private static final int APROX_MOCK_PORT = 12345;

    private static final String APROX_MOCK_HOST = "http://localhost:" + APROX_MOCK_PORT;

    private String currentAproxHost;

    @Inject
    private Configuration configuration;

    @Before
    public void setUp() throws ConfigurationParseException {
        currentAproxHost = configuration.getConfig().getAproxServer();
        configuration.getConfig().setAproxServer(APROX_MOCK_HOST);
    }

    public Undertow getUndertowServer(PathHandler handler) {
        return Undertow.builder().addHttpListener(APROX_MOCK_PORT, "localhost").setHandler(handler)
                .build();
    }

    @After
    public void teardown() throws ConfigurationParseException {
        configuration.getConfig().setAproxServer(currentAproxHost);
    }

    @Test
    public void testGetDependencyTreeOfGav() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();
    }

    @Test
    public void testGetVersionsOfGav() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();

    }

    @Test
    public void testGetPom() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();

    }

    @Test
    public void testAddRepositoryToGroup() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();

    }

    @Test
    public void testRemoveRepositoryFromGroup() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();

    }

    @Test
    public void testGetAllRepositoriesFromGroup() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();

    }

    @Test
    public void testDoesGAVExistInPublicRepo() {
        PathHandler handler = new PathHandler();
        handler.addExactPath("/loco", new HttpHandler() {

            public void handleRequest(HttpServerExchange exchange) throws Exception {
                exchange.getResponseSender().send("No surprises");
            }
        });

        Undertow server = getUndertowServer(handler);
        server.start();
        // do tests

        server.stop();

    }
}
