package dsa.challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NonConstructibleChangeTest {

    @Test
    void testHappyPath() {
        assertEquals(2, NonConstructibleChange.getMin(new int[]{1}));
        assertEquals(1, NonConstructibleChange.getMin(new int[]{2}));
        assertEquals(4, NonConstructibleChange.getMin(new int[]{1, 1, 1}));
        assertEquals(6, NonConstructibleChange.getMin(new int[]{1, 1, 3}));
        assertEquals(6, NonConstructibleChange.getMin(new int[]{1, 1, 1, 1, 1}));
        assertEquals(7, NonConstructibleChange.getMin(new int[]{1, 2, 3}));
        assertEquals(4, NonConstructibleChange.getMin(new int[]{1, 2, 5}));
        assertEquals(20, NonConstructibleChange.getMin(new int[]{5, 7, 1, 1, 2, 3, 22}));
    }

    @Test
    void testEmptyArray() {
        int[] coins = {};
        assertEquals(1, NonConstructibleChange.getMin(coins));
    }

}