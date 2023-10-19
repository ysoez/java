package concurrency.thread.metadata;

import java.util.concurrent.TimeUnit;

class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getState());
        });
        System.out.println(thread.getState());
        thread.start();
        thread.join();
        System.out.println(thread.getState());
    }

}
