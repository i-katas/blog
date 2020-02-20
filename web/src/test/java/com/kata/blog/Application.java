package com.kata.blog;

import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;
import io.undertow.client.ClientCallback;
import io.undertow.client.ClientConnection;
import io.undertow.client.ClientExchange;
import io.undertow.client.ClientRequest;
import io.undertow.client.ClientResponse;
import io.undertow.client.UndertowClient;
import io.undertow.server.DefaultByteBufferPool;
import org.xnio.Xnio;
import org.xnio.XnioWorker;
import org.xnio.channels.StreamSourceChannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import static io.undertow.util.Headers.HOST;
import static io.undertow.util.Methods.GET;
import static org.xnio.OptionMap.EMPTY;

/**
 * @author i-katas
 * @since 1.0
 */
public class Application {
    private static final URI site = URI.create("http://localhost:8080");
    private final UndertowClient client = UndertowClient.getInstance();
    private ClientConnection connection;
    private Server server;

    public void start() throws IOException {
        server = Server.run(new String[]{site.getHost(), Integer.toString(site.getPort())});
        connection = connection();
    }

    private ClientConnection connection() throws IOException {
        XnioWorker worker = Xnio.getInstance().createWorker(EMPTY);
        return client.connect(site, worker, new DefaultByteBufferPool(false, 17 * 1024), EMPTY).get();
    }

    public void stop() throws IOException {
        if (connection != null) {
            connection.close();
        }
        if (server != null) {
            server.stop();
        }
    }

    public JsonAsserter list() throws IOException, InterruptedException {
        ResponseHandler response = new ResponseHandler();
        connection.sendRequest(get("/blogs"), response);
        return JsonAssert.with(response.bodyAsString());
    }

    private ClientRequest get(String uri) {
        ClientRequest request = new ClientRequest().setMethod(GET).setPath(uri);
        request.getRequestHeaders().put(HOST, site.getHost());
        return request;
    }

    private static class ResponseHandler implements ClientCallback<ClientExchange> {
        private final CountDownLatch lock = new CountDownLatch(1);
        private IOException exception;
        private StreamSourceChannel channel;

        @Override
        public void completed(ClientExchange result) {
            if (isNotReady(result)) {
                result.setResponseListener(this);
                return;
            }
            ClientResponse response = result.getResponse();
            if (isFailed(response)) {
                failed(new IOException(result.getRequest().getPath() + " " + response.getStatus() + "/" + response.getResponseCode()));
                return;
            }
            channel = result.getResponseChannel();
            lock.countDown();
        }

        private boolean isNotReady(ClientExchange result) {
            return result.getResponse() == null;
        }

        private boolean isFailed(ClientResponse response) {
            return response.getResponseCode() != 200;
        }

        @Override
        public void failed(IOException e) {
            this.exception = e;
            lock.countDown();
        }

        public String bodyAsString() throws IOException, InterruptedException {
            lock.await();
            if (exception != null) {
                throw exception;
            }
            try (StreamSourceChannel channel = this.channel) {
                return Application.toString(channel, StandardCharsets.UTF_8);
            }
        }

    }

    private static String toString(ReadableByteChannel channel, Charset encoding) throws IOException {
        try (ByteArrayOutputStream target = new ByteArrayOutputStream(); WritableByteChannel out = Channels.newChannel(target)) {
            for (ByteBuffer buff = ByteBuffer.allocate(1024); channel.read(buff) != -1; ) {
                buff.flip();
                out.write(buff);
                buff.clear();
            }
            return new String(target.toByteArray(), encoding);
        }
    }
}
