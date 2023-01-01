package persistence.util;

public class ConcurrencyUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}
