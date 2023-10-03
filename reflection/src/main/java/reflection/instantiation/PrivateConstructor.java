package reflection.instantiation;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;

public class PrivateConstructor {

    public static void main(String[] args) throws Exception {
        initConfiguration();
        WebServer webServer = new WebServer();
        webServer.start(ServerConfiguration.getInstance());
    }

    private static void initConfiguration() throws Exception {
        Constructor<ServerConfiguration> constructor = ServerConfiguration.class.getDeclaredConstructor(int.class, String.class);
        constructor.setAccessible(true);
        constructor.newInstance(8080, "Hello World");
    }

    static class ServerConfiguration {
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

        public static ServerConfiguration getInstance() {
            return instance;
        }

        public InetSocketAddress getServerAddress() {
            return this.serverAddress;
        }

        public String getGreetingMessage() {
            return this.greetingMessage;
        }
    }

    static class WebServer {
        public void start(ServerConfiguration configuration) throws IOException {
            InetSocketAddress serverAddress = configuration.getServerAddress();
            HttpServer httpServer = HttpServer.create(serverAddress, 0);

            httpServer.createContext("/home").setHandler(exchange -> {
                String responseMessage = ServerConfiguration.getInstance().getGreetingMessage();
                exchange.sendResponseHeaders(200, responseMessage.length());
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(responseMessage.getBytes());
                responseBody.flush();
                responseBody.close();
            });

            System.out.printf("Starting server on address %s:%d%n",
                    ServerConfiguration.getInstance().getServerAddress().getHostName(),
                    ServerConfiguration.getInstance().getServerAddress().getPort());

            httpServer.start();
        }
    }

}
