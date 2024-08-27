package library.concurrency.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

class ConcurrentHashMapFailSafeIterator {

    public static void main(String[] args) throws InterruptedException {
        var data = Map.of(1, "A", 2, "B", 3, "C");
        var concurrentHashMap = new ConcurrentHashMap<>(data);
        updateConcurrently(concurrentHashMap);
        var hashMap = new HashMap<>(data);
        updateConcurrently(hashMap);
    }

    private static void updateConcurrently(Map<Integer, String> map) throws InterruptedException {
        System.out.println(map.getClass().getName());
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.put(4, "D");
        }).start();
        for (var entry : map.entrySet()) {
            System.out.println(entry);
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

}
