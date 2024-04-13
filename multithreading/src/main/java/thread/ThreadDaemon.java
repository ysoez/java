package thread;

class ThreadDaemon {

    @SuppressWarnings("StatementWithEmptyBody")
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() -> {
            while (true) {
                // ~ no op
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

}
