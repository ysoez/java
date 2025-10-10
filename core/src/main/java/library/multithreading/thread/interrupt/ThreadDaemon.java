package library.multithreading.thread.interrupt;

import java.util.concurrent.TimeUnit;

class ThreadDaemon {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            for (int i = 0; true; i++) {
                System.out.println(i);
            }
        });
        thread.setDaemon(true);
        thread.start();

        TimeUnit.SECONDS.sleep(1);
    }

}
