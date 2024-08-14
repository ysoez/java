package library.concurrency.thread;

class ThreadInterruptedException {

    public static void main(String[] args) {
        var thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500_000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread due to interruption");
            }
        }
    }

}
