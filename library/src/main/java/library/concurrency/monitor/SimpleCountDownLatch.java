package library.concurrency.monitor;

class SimpleCountDownLatch {

    private int count;

    SimpleCountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
        this.count = count;
    }

    /**
     * Causes the current thread to wait until the latch has counted down to zero.
     * If the current count is already zero then this method returns immediately.
     */
    void await() throws InterruptedException {
        synchronized (this) {
            while (count > 0) {
                this.wait();
            }
        }
    }

    /**
     * Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
     * If the current count already equals zero then nothing happens.
     */
    void countDown() {
        synchronized (this) {
            if (count > 0) {
                count--;
                if (count == 0) {
                    this.notifyAll();
                }
            }
        }
    }

    int getCount() {
        return this.count;
    }

}