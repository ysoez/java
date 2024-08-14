package multithreading;

public class ConcurrencyUtils {

    public static void waitOn(Object monitor) {
        try {
            monitor.wait();
        } catch (InterruptedException e) {
            // ~ no op
        }
    }

}
