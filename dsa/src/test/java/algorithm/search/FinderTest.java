package algorithm.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinderTest {

    @Test
    void testMostFrequent() {
        // ~ basic
        assertEquals(3, Finder.mostFrequent(new Integer[]{1, 2, 2, 3, 3, 3, 4}));
        assertEquals(5, Finder.mostFrequent(new Integer[]{5, 5, 5, 1, 1, 2, 2, 2}));
        assertEquals("banana", Finder.mostFrequent(new String[]{"apple", "banana", "apple", "orange", "banana", "banana"}));
        assertEquals('a', Finder.mostFrequent(new Character[]{'a', 'b', 'c', 'a', 'a', 'b'}));
        assertEquals(3.3, Finder.mostFrequent(new Double[]{1.1, 2.2, 1.1, 3.3, 3.3, 3.3}));
        // ~ edge
        assertNull(Finder.mostFrequent(new Integer[]{}));
        assertEquals(42, Finder.mostFrequent(new Integer[]{42}));
        assertEquals(3, Finder.mostFrequent(new Integer[]{3, 3, 3, 3, 3}));
        assertEquals(1, Finder.mostFrequent(new Integer[]{1, 2, 3, 4, 5, 6}));
    }

}