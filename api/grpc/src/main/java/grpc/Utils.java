package grpc;

public class Utils {

    // ~ port 0 means that the operating system will pick an available port to use.
    public static final int RANDOM_PORT = 0;

    public static void registerShutdownHook(ShutdownCallback callback) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                callback.run();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    public interface ShutdownCallback {
        void run() throws InterruptedException;
    }

}
