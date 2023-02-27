package algorithm.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FirstRepeatedCharFinderTest {

    @Test
    void search() {
        assertEquals('e', new FirstRepeatedCharFinder().search("a green apple"));
        assertEquals('e', new FirstRepeatedCharFinder().search("geeksforgeeks"));
        assertEquals(Character.MIN_VALUE, new FirstRepeatedCharFinder().search("abcde"));
    }

}