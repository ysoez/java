package algorithm.search;

import org.junit.jupiter.api.Test;

import static algorithm.search.BinarySearch.searchIterative;
import static algorithm.search.BinarySearch.searchRecursive;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTest {

    @Test
    void recursive() {
        int[] numbers = {1, 3, 5, 6, 7};
        assertEquals(0, searchRecursive(numbers, 1));
        assertEquals(2, searchRecursive(numbers, 5));
        assertEquals(4, searchRecursive(numbers, 7));
        assertEquals(-1, searchRecursive(numbers, 70));
    }
    @Test
    void iterative() {
        int[] numbers = {1, 3, 5, 6, 7};
        assertEquals(0, searchIterative(numbers, 1));
        assertEquals(2, searchIterative(numbers, 5));
        assertEquals(4, searchIterative(numbers, 7));
        assertEquals(-1, searchIterative(numbers, 70));
    }

}