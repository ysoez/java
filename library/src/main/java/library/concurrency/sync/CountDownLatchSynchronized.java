package library.concurrency.sync;

class CountDownLatchSynchronized {

    private int count;

    CountDownLatchSynchronized(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
        this.count = count;
    }

    void await() throws InterruptedException {
        //
        // ~ causes the current thread to wait until the latch has counted down to zero
        // ~ if the current count is already zero then this method returns immediately
        //
        synchronized (this) {
            while (count > 0) {
                this.wait();
            }
        }
    }

    void countDown() {
        //
        // ~ decrements the count of the latch, releasing all waiting threads when the count reaches zero
        // ~ if the current count already equals zero then nothing happens
        //
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