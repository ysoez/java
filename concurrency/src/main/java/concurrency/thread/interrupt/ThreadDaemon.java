package concurrency.thread.interrupt;

class ThreadDaemon {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            for (int i = 0; i < Long.MAX_VALUE; i++) {
                i *= 2;
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

}
