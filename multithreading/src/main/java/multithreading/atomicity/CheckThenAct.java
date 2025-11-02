package multithreading.atomicity;

import java.util.concurrent.atomic.AtomicReference;

public class CheckThenAct {

    private static final AtomicReference<String> value = new AtomicReference<>("v0");

    public static void main(String[] args) throws InterruptedException {
        var t1 = new Thread(() -> {
            var expected = value.get();
            boolean updated = value.compareAndSet(expected, "v100");
            System.out.println("updater1 success: " + updated);
        });
        var t2 = new Thread(() -> {
            var expected = value.get();
            boolean updated = value.compareAndSet(expected, "v200");
            System.out.println("updater2 success: " + updated);
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("final version: " + value.get());
    }

}
