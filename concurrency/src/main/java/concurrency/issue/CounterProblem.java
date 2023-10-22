package concurrency.issue;

import lombok.RequiredArgsConstructor;

class CounterProblem {

    public static void main(String[] args) throws InterruptedException {
        var inventoryCounter = new InventoryCounter();
        var incrementingThread = new IncrementingThread(inventoryCounter);
        var decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("Items count: " + inventoryCounter.items);
    }

    @RequiredArgsConstructor
    private static class DecrementingThread extends Thread {

        private final InventoryCounter inventoryCounter;

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    @RequiredArgsConstructor
    private static class IncrementingThread extends Thread {

        private final InventoryCounter inventoryCounter;

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

        public void increment() {
            items++;
        }

        public void decrement() {
            items--;
        }
    }
}
