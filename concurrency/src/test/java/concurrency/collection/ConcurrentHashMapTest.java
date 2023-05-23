package concurrency.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrentHashMapTest {

    @Test
    void testPutAndGet() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        assertEquals(1, map.get("A"));
        assertEquals(2, map.get("B"));
        assertEquals(3, map.get("C"));
        assertNull(map.get("D"));
    }

    @Test
    void testRemove() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        map.remove("B");

        assertEquals(1, map.get("A"));
        assertNull(map.get("B"));
        assertEquals(3, map.get("C"));
    }

    @Test
    void testConcurrentPutAndGet() throws InterruptedException {
        final int numThreads = 100;
        final int numOperations = 1000;
        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < numOperations; j++) {
                    map.put(j, j);
                    assertEquals(j, map.get(j));
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 0; i < numOperations; i++) {
            assertEquals(i, map.get(i));
        }
    }

}