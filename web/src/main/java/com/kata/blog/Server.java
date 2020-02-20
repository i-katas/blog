package com.kata.blog;

import io.undertow.Undertow;
import io.undertow.util.Headers;

/**
 * @author i-katas
 * @since 1.0
 */
public class Server {

    private final Undertow server;

    public Server(int serverPort) {
        server = Undertow.builder().addHttpListener(serverPort, "localhost", exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send("[]");
        }).build();
    }

    public static void main(String[] args) {
        run(args);
    }

    public static Server run(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
        return server;
    }

    private void start() {
        server.start();
    }

    public void stop() {
        server.stop();
    }
}
