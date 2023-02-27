package algorithm.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstUniqueCharFinderTest {

    @Test
    void search() {
        assertEquals('l', new FirstUniqueCharFinder().search("leetcode"));
        assertEquals('v', new FirstUniqueCharFinder().search("loveleetcode"));
        assertEquals(Character.MIN_VALUE, new FirstUniqueCharFinder().search("aabb"));
    }

}