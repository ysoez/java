package library.concurrency.thread;

import static library.concurrency.ConcurrencyUtils.log;

class ThreadStart {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            log("started");
            for (int i = 0; i < 4; i++) {
                System.out.println("i = " + i);
            }
            log("completed");
        });
        thread.start();
        thread.join();
        log("completed");
    }

}
