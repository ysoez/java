package concurrency.thread;

class ThreadDaemon {

    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            //noinspection StatementWithEmptyBody
            while (true) {
                // no op
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

}
