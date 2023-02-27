package algorithm.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxFinderTest {

    @Test
    void max() {
        var maxFinder = new MaxFinder();

        assertThrows(IllegalArgumentException.class, () -> maxFinder.search(null));
        assertThrows(IllegalArgumentException.class, () -> maxFinder.search(new int[0]));

        assertEquals(1, maxFinder.search(new int[]{1}));

        assertEquals(2, maxFinder.search(new int[]{1, 2}));
        assertEquals(2, maxFinder.search(new int[]{2, 1}));

        assertEquals(78, maxFinder.search(new int[]{10, 21, 16, 78, 62, 5}));
        assertEquals(30, maxFinder.search(new int[]{10, 20, 30}));
    }

}
