package thread;

class ThreadRunnable {

    public static void main(String[] args) {
        var thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("i = " + i);
            }
        });
        thread.start();
    }

}
