package com.kata.blog;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;

import static io.undertow.util.Headers.CONTENT_TYPE;

/**
 * @author i-katas
 * @since 1.0
 */
public class Server {
    private static final int ARG_SERVER_PORT = 1;
    private static final int ARG_SERVER_HOST = 0;

    private final Undertow server;

    public Server(String host, int serverPort) {
        server = Undertow.builder().addHttpListener(serverPort, host, displayBlogs()).build();
    }

    private HttpHandler displayBlogs() {
        return exchange -> {
            exchange.getResponseHeaders().put(CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send("[]");
        };
    }

    public static void main(String[] args) {
        run(args);
    }

    public static Server run(String[] args) {
        Server server = new Server(args[ARG_SERVER_HOST], Integer.parseInt(args[ARG_SERVER_PORT]));
        server.start();
        return server;
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop();
    }
}
