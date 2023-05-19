package algorithm.caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WriteBehindCacheTest {

    private WriteBehindCache<Integer, String> cache;
    private Set<String> writeOperations;
    private Set<Integer> deleteOperations;

    @BeforeEach
    void setUp() {
        writeOperations = new HashSet<>();
        deleteOperations = new HashSet<>();
        cache = new WriteBehindCache<>(new WriteBehindCache.WriteBehindHandler<>() {
            @Override
            public void writeAsync(Integer key, String value) {
                CompletableFuture.runAsync(() -> writeOperations.add(key + "=" + value));
            }
            @Override
            public void deleteAsync(Integer key) {
                CompletableFuture.runAsync(() -> deleteOperations.add(key));
            }
        });
    }

    @Test
    void testCacheOperations() {
        cache.put(1, "Value1");
        cache.put(2, "Value2");
        cache.put(3, "Value3");

        assertEquals("Value1", cache.get(1));
        assertEquals("Value2", cache.get(2));
        assertEquals("Value3", cache.get(3));

        cache.remove(2);
        assertNull(cache.get(2));

        cache.put(4, "Value4");
        cache.remove(1);
        assertNull(cache.get(1));

        await().until(() -> Objects.equals(Set.of("1=Value1", "2=Value2", "3=Value3", "4=Value4"), writeOperations));
        await().until(() -> Objects.equals(Set.of(2, 1), deleteOperations));
    }
}
