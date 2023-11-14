package zk;

class ZkUtils  {

    static final int SESSION_TIMEOUT = 3000;

    interface ToxicRunnable {
        void run() throws Exception;
    }

    static Runnable wrap(ToxicRunnable runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
