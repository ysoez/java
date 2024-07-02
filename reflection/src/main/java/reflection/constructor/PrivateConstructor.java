package reflection.constructor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;

class PrivateConstructor {

    public static void main(String[] args) throws Exception {
        initConfiguration();
        var webServer = new WebServer();
        webServer.start(ServerConfiguration.instance);
    }

    private static void initConfiguration() throws Exception {
        Constructor<ServerConfiguration> constructor = ServerConfiguration.class.getDeclaredConstructor(int.class, String.class);
        constructor.setAccessible(true);
        constructor.newInstance(8080, "Good Day");
    }

    private static class ServerConfiguration {

        private static ServerConfiguration instance;
        private final InetSocketAddress serverAddress;
        private final String greetingMessage;

        private ServerConfiguration(int port, String greetingMessage) {
            this.greetingMessage = greetingMessage;
            this.serverAddress = new InetSocketAddress("localhost", port);
            if (instance == null) {
                instance = this;
            }
        }

    }

    private static class WebServer {

        void start(ServerConfiguration configuration) throws IOException {
            InetSocketAddress serverAddress = configuration.serverAddress;
            HttpServer httpServer = HttpServer.create(serverAddress, 0);

            // ~ add routes
            httpServer.createContext("/home").setHandler(exchange -> {
                String responseMessage = ServerConfiguration.instance.greetingMessage;
                exchange.sendResponseHeaders(200, responseMessage.length());
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(responseMessage.getBytes());
                responseBody.flush();
                responseBody.close();
            });

            // ~ run
            System.out.printf("Starting server on address %s:%d%n",
                    ServerConfiguration.instance.serverAddress.getHostName(),
                    ServerConfiguration.instance.serverAddress.getPort());
            httpServer.start();
        }

    }

}
