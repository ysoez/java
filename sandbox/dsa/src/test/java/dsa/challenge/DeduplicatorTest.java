package dsa.challenge;

import dsa.array.Arrays;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dsa.challenge.Deduplicator.ArrayDeduplicator.INSTANCE;
import static org.junit.jupiter.api.Assertions.*;

class DeduplicatorTest {

    @Nested
    class ArrayDeduplicator {
        @Test
        void testEmptyArray() {
            assertNull(INSTANCE.apply(null));
            assertArrayEquals(Arrays.EMPTY_INT_ARR, INSTANCE.apply(Arrays.EMPTY_INT_ARR));
        }
        @Test
        void testSingleElementArray() {
            assertArrayEquals(new int[]{10}, INSTANCE.apply(new int[]{10}));
        }
        @Test
        void testAllDuplicates() {
            assertArrayEquals(new int[]{7}, INSTANCE.apply(new int[]{7, 7, 7, 7}));
        }
        @Test
        void testNoDuplicates() {
            assertArrayEquals(new int[]{1, 2, 3, 4}, INSTANCE.apply(new int[]{1, 2, 3, 4}));
        }
        @Test
        void testDeduplicateAndPreserveOrder() {
            assertArrayEquals(new int[]{3, 1, 2}, INSTANCE.apply(new int[]{3, 1, 2, 3, 2, 1}));
        }
    }

}