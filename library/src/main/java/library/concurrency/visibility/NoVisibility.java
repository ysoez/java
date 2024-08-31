package library.concurrency.visibility;

class NoVisibility {

    private static boolean ready;
    private static int number;

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        public void run() {
            //
            // ~ may loop forever
            //
            while (!ready)
                Thread.yield();
            //
            // ~ may print 0 or 42
            //
            System.out.println(number);
        }
    }

}