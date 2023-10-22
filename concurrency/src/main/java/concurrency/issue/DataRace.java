package concurrency.issue;

class DataRace {

    public static void main(String[] args) {
        var sharedResource = new SharedResource();
        var thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedResource.increment();
            }
        });
        var thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedResource.checkForDataRace();
            }

        });
        thread1.start();
        thread2.start();
    }

    private static class SharedResource {
//        private volatile int x = 0;
        private int x = 0;
//        private volatile int y = 0;
        private int y = 0;

        void increment() {
            x++;
            y++;
        }

        void checkForDataRace() {
            if (y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}
