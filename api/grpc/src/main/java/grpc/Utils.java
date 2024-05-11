package grpc;

import io.grpc.ChannelCredentials;
import io.grpc.InsecureChannelCredentials;
import io.grpc.Server;

import java.util.concurrent.TimeUnit;

public class Utils {

    // ~ port 0 means that the operating system will pick an available port to use.
    public static final int RANDOM_PORT = 0;

    public static final ChannelCredentials INSECURE_CHANNEL_CREDENTIALS = InsecureChannelCredentials.create();

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public static void blockUntilShutdown(Server server) throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void addShutdownHook(Server server) {
        if (server != null) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // ~ use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }));
        }
    }

}
