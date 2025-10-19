package multithreading;

import java.util.concurrent.TimeUnit;

class Visibility {

    public static void main(String[] args) throws InterruptedException {
        var flag = new VolatileFlag();
        new Thread(() -> {
            while (flag.isRunning)
                System.out.println("running");
            System.out.println("finished");
        }).start();
        TimeUnit.MILLISECONDS.sleep(500);
        flag.isRunning = false;
    }

    private static class UnsafeFlag {
        private boolean isRunning = true;
    }

    private static class VolatileFlag {
        private volatile boolean isRunning = true;
    }

}
